import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { BarmanPageComponent } from './pages/barman-page/barman-page.component';
import { CookPageComponent } from './pages/cook-page/cook-page.component';
import { ManagerPageComponent } from './pages/manager-page/manager-page/manager-page.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { WaiterPageComponent } from './pages/waiter-page/waiter-page.component';
import { GuestPageComponent } from './pages/guest-page/guest-page.component';
import { AdminPageComponent } from './pages/admin-page/admin-page.component';
import { AuthGuard } from './auth-guard';

const routes: Routes = [
  {
    path: 'cook',
    component: CookPageComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'cook',
    },
  },
  {
    path: 'barman',
    component: BarmanPageComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'barman',
    },
  },
  {
    path: 'waiter',
    component: WaiterPageComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'waiter',
    },
  },
  {
    path: 'manager',
    component: ManagerPageComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'manager',
    },
  },
  {
    path: 'admin',
    component: AdminPageComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'admin',
    },
  },
  {
    path: 'guest',
    component: GuestPageComponent,
  },
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
