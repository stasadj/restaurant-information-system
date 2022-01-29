import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Staff } from 'src/app/model/Staff';
import { StaffService } from 'src/app/services/staff/staff.service';
import { UserProfileComponent } from '../user-profile/user-profile.component';

@Component({
  selector: 'app-staff-board',
  templateUrl: './staff-board.component.html',
  styleUrls: ['./staff-board.component.less']
})
export class StaffBoardComponent implements OnInit {

  staffMembers: Staff[] = [];

  constructor(private staffService: StaffService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.staffService.getAllStaff().subscribe(staffMembers => {
      this.staffMembers = staffMembers;
    });
  }

  viewProfile(id: number) {
    let dialog = this.dialog.open(UserProfileComponent, { width: '50%', height: '60%' });
    dialog.componentInstance.staffId = id;
  }

}
