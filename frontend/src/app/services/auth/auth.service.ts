import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly path: string = '/api/auth';

  readonly loggedUser: Subject<any> = new Subject();

  private http: HttpClient;
  constructor(private handler: HttpBackend, private toastr: ToastrService) {
    this.http = new HttpClient(handler);
  }

  login(credentials: { username: string; password: string }) {
    this.http
      .post<{ token: string }>(`${this.path}/login`, credentials)
      .subscribe({
        next: (d) => {
          console.log(d), localStorage.setItem('token', d.token);
          this.whoAmI(d.token);
        },
        error: () => {
          this.loggedUser.next(null);
          this.toastr.error('Incorrect username or password.');
        },
      });
  }

  pinLogin(pin: number) {
    this.http.post<{ token: string }>(`${this.path}/pin-login`, pin).subscribe({
      next: (d) => {
        console.log(d), localStorage.setItem('token', d.token);
        this.whoAmI(d.token);
      },
      error: () => {
        this.loggedUser.next(null);
        this.toastr.error('Incorrect pin.');
      },
    });
  }

  whoAmI(token: string) {
    this.http
      .get(`${this.path}/whoami`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .subscribe((d: any) => {
        console.log(d);
        localStorage.setItem('userId', d.id);
        localStorage.setItem('role', d.role);
        this.loggedUser.next(d);
      });
  }
}
