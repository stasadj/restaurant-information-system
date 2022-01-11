import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly path: string = '/api/auth';

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }) {
    this.http
      .post<{ token: string }>(`${this.path}/login`, credentials)
      .subscribe((d) => {
        console.log(d), localStorage.setItem('token', d.token);
        this.whoAmI();
      });
  }

  pinLogin(pin: number) {
    this.http
      .post<{ token: string }>(`${this.path}/pin-login`, pin)
      .subscribe((d) => {
        console.log(d), localStorage.setItem('token', d.token);
        this.whoAmI();
      });
  }

  logout() {
    localStorage.removeItem('token');
    //TODO return to login page
  }

  whoAmI() {
    this.http.get(`${this.path}/whoami`).subscribe((d: any) => {
      console.log(d);
      localStorage.setItem('userId', d.id);
      localStorage.setItem('role', d.role);
    });
  }
}
