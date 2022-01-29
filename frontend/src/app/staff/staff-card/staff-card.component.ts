import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Staff } from 'src/app/model/Staff';
import { StaffFormComponent } from '../staff-form/staff-form.component';
import { UserProfileComponent } from '../user-profile/user-profile.component';

@Component({
  selector: 'app-staff-card',
  templateUrl: './staff-card.component.html',
  styleUrls: ['./staff-card.component.less']
})
export class StaffCardComponent implements OnInit {
  
  @Input() staff?: Staff;

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  viewProfile(id: number) {
    let dialog = this.dialog.open(UserProfileComponent, { width: '50%', height: '60%' });
    dialog.componentInstance.staffId = id;
  }

  editProfile(staff: Staff) {
    let dialog = this.dialog.open(StaffFormComponent, { width: '50%', height: '60%' });
    dialog.componentInstance.setStaff(staff);
  }

}
