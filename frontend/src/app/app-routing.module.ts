import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
<<<<<<< HEAD
import { ItemComponent } from './itemTest/item/item/item.component';

const routes: Routes = [
    {
        path: "item-test",
        pathMatch: "full",
        component: ItemComponent
        
    }
=======
import { LoginComponent } from './login/login.component';
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
    component: CookPageComponent,
  },
  {
    path: 'waiter',
    component: WaiterPageComponent,
  },
  { path: 'login', component: LoginComponent },
  { path: '**', component: NotFoundComponent },
>>>>>>> develop
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
