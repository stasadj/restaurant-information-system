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

  constructor(private staffService: StaffService) { }

  ngOnInit(): void {
    this.staffService.getAllStaff().subscribe(staffMembers => {
      this.staffMembers = staffMembers;
    });
  }

}
