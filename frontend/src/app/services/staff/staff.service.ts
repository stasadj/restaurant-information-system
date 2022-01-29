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

  getAllStaff(): Observable<Staff[]> {
    return this.http.get<Staff[]>(
      `${this.path}/all`
    );
  }

  getStaffMemberById(id?: number): Observable<Staff> {
    return this.http.get<Staff>(
      `${this.path}/${id ?? localStorage.getItem('userId')}`
    );
  }

  getStaffPaymentItems(id?: number): Observable<StaffPaymentItem[]> {
    return this.http.get<StaffPaymentItem[]>(
      `${this.path}/payment/${id ?? localStorage.getItem('userId')}`
    );
  }
}
