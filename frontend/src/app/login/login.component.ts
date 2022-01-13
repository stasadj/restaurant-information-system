import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
})
export class LoginComponent implements OnInit, OnDestroy {
  username: string = '';
  password: string = '';
  pin: number = 0;

  constructor(
    private auth: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.auth.loggedUser.subscribe((user) => {
      if (user) this.router.navigate([`/${user.role}`]);
      else this.toastr.error('Incorrect');
    });
  }
  ngOnDestroy(): void {
    this.auth.loggedUser.unsubscribe();
  }

  onLogin() {
    this.auth.login({ username: this.username, password: this.password });
  }

  onPinLogin() {
    this.auth.pinLogin(this.pin);
  }

  onLogout() {
    this.auth.logout();
  }
}
