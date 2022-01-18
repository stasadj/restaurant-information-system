import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemComponent } from './itemTest/item/item/item.component';

const routes: Routes = [
    {
        path: "item-test",
        pathMatch: "full",
        component: ItemComponent
        
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
