import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
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

  constructor(
    private auth: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    let s = this.auth.loggedUser.subscribe((user) => {
      if (user) this.router.navigate([`/${user.role}`]);
      else this.toastr.error('Incorrect');
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
