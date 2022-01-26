import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { BarmanPageComponent } from './pages/barman-page/barman-page.component';
import { CookPageComponent } from './pages/cook-page/cook-page.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { WaiterPageComponent } from './pages/waiter-page/waiter-page.component';

const routes: Routes = [
  {
    path: 'cook',
    component: CookPageComponent,
  },
  {
    path: 'barman',
    component: BarmanPageComponent,
  },
  {
    path: 'waiter',
    component: WaiterPageComponent,
  },
  { path: 'login', component: LoginComponent },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
