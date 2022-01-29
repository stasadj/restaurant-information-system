import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Staff } from 'src/app/model/Staff';
import { StaffPaymentItem } from 'src/app/model/StaffPaymentItem';

@Injectable({
  providedIn: 'root',
})
export class StaffService {
  private readonly path = '/api/staff';

  constructor(private http: HttpClient) {}

  getStaffMemberById(): Observable<Staff> {
    return this.http.get<Staff>(
      `${this.path}/${localStorage.getItem('userId')}`
    );
  }

  getStaffPaymentItems(): Observable<StaffPaymentItem[]> {
    return this.http.get<StaffPaymentItem[]>(
      `${this.path}/payment/${localStorage.getItem('userId')}`
    );
  }
}
