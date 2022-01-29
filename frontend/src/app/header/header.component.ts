import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { UserProfileComponent } from '../staff/user-profile/user-profile.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.less'],
})
export class HeaderComponent implements OnInit {
  constructor(public router: Router, public dialog: MatDialog) {}

  ngOnInit(): void {}

  onLogout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  onProfile() {
    this.dialog.open(UserProfileComponent, { width: '50%', height: '60%' });
  }
}
