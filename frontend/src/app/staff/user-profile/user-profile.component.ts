import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material/sort';
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

  sortedData: StaffPaymentItem[] = [];

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
      this.sortedData = res.slice();
      console.log(res);
    });
  }

  sortData(sort: Sort) {
    const data = this.staffPaymentItems.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'date':
          return compare(a.dateTime, b.dateTime, isAsc);
        case 'amount':
          return compare(a.amount, b.amount, isAsc);
        default:
          return 0;
      }
    });
  }
}

function compare(a: number | Date, b: number | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
