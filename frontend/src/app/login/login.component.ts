import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
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
  private subscriptions = new Subscription();
  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  error: string = '';

  submit() {}

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit(): void {
    let s = this.auth.loggedUser.subscribe((user) => {
      if (user) this.router.navigate([`/${user.role}`]);
    });
    this.subscriptions.add(s);
  }
  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  onLogin() {
    this.auth.login({ username: this.username, password: this.password });
  }

  onPinLogin() {
    this.auth.pinLogin(this.pin);
  }
}
