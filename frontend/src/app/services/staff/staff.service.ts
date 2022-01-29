import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Staff } from 'src/app/model/Staff';

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
}
