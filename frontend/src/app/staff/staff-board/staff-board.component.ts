import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Staff } from 'src/app/model/Staff';
import { StaffService } from 'src/app/services/staff/staff.service';
import { StaffFormComponent } from '../staff-form/staff-form.component';

@Component({
  selector: 'app-staff-board',
  templateUrl: './staff-board.component.html',
  styleUrls: ['./staff-board.component.less']
})
export class StaffBoardComponent implements OnInit {

  staffMembers: Staff[] = [];
  isAdmin: boolean = true;

  constructor(private staffService: StaffService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.isAdmin = localStorage.getItem('role') === 'admin';
    this.staffService.getAllStaff().subscribe(staffMembers => {
      this.staffMembers = staffMembers;
    });
  }

  removeStaffMember(staff: Staff) {
    this.staffMembers.splice(this.staffMembers.indexOf(staff), 1);
  }

  createStaff() {
    let dialog = this.dialog.open(StaffFormComponent, { width: '50%', height: '60%' });
    dialog.componentInstance.createEvent.subscribe(staff => {
      dialog.componentInstance.createEvent.unsubscribe();
      this.staffMembers.push(staff);
    });
    dialog.componentInstance.setupCreation();
  }

}
