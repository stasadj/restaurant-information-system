import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  pin: number = 0;

  constructor(private auth: AuthService) {}

  ngOnInit(): void {}

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
