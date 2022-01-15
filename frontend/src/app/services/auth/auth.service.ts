import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly path: string = '/api/auth';

  readonly loggedUser: Subject<any> = new Subject();

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }) {
    this.http
      .post<{ token: string }>(`${this.path}/login`, credentials)
      .subscribe({
        next: (d) => {
          console.log(d), localStorage.setItem('token', d.token);
          this.whoAmI();
        },
        error: () => this.loggedUser.next(null),
      });
  }

  pinLogin(pin: number) {
    this.http.post<{ token: string }>(`${this.path}/pin-login`, pin).subscribe({
      next: (d) => {
        console.log(d), localStorage.setItem('token', d.token);
        this.whoAmI();
      },
      error: () => this.loggedUser.next(null),
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
      this.loggedUser.next(d);
    });
  }
}
