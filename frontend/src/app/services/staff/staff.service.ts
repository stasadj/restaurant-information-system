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

  constructor(private http: HttpClient) { }

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

  deleteStaff(id?: number): void {
    this.http.delete(`${this.path}/${id}`).subscribe();
  }

  getStaffPaymentItems(id?: number): Observable<StaffPaymentItem[]> {
    return this.http.get<StaffPaymentItem[]>(
      `${this.path}/payment/${id ?? localStorage.getItem('userId')}`
    );
  }

  updateStaff(staff: Staff): Observable<Staff> {
    return this.http.put<Staff>(`${this.path}/edit`, staff);
  }

  changeSalary(staffId: number, monthlyWage: number): Observable<Staff> {
    return this.http.put<Staff>(`${this.path}/change-salary`, { staffId, monthlyWage });
  }

  changePin(currentPin: number, newPin: number): Observable<Staff> {
    return this.http.put<Staff>(`${this.path}/change-pin`, { currentPin, newPin });
  }

  createStaff(staff: Staff): Observable<Staff> {
    return this.http.post<Staff>(`${this.path}/create`, staff);
  }
}
