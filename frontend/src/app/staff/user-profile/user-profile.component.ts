import { Component, OnInit } from '@angular/core';
import { Staff } from 'src/app/model/Staff';
import { StaffPaymentItem } from 'src/app/model/StaffPaymentItem';
import { StaffService } from 'src/app/services/staff/staff.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.less'],
})
export class UserProfileComponent implements OnInit {
  staff: Staff = {
    id: 0,
    firstName: '',
    lastName: '',
    phoneNumber: '',
    email: '',
    monthlyWage: 0,
  };

  staffPaymentItems: StaffPaymentItem[] = [];

  getAverage(): number {
    return (
      this.staffPaymentItems.reduce((prev, curr) => prev + curr.amount, 0) /
      this.staffPaymentItems.length
    );
  }

  constructor(private staffService: StaffService) {}

  ngOnInit(): void {
    this.staffService.getStaffMemberById().subscribe((res) => {
      this.staff = res;
    });

    this.staffService.getStaffPaymentItems().subscribe((res) => {
      this.staffPaymentItems = res;
      console.log(res);
    });
  }
}
