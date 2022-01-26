import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './services/auth/auth.interceptor';

import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DragDropModule } from '@angular/cdk/drag-drop';

import { AppRoutingModule } from './app-routing.module';
//import { AngularMaterialModule } from './angular-material.module';

import { AppComponent } from './app.component';
import { CanvasComponent } from './restaurant-map/canvas/canvas.component';
import { TableComponent } from './restaurant-map/table/table.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MapComponent } from './restaurant-map/map/map.component';
import { TableOrderComponent } from './order-components/table-order/table-order.component';
import { OrderBoardComponent } from './order-components/order-board/order-board.component';
import { OrderCardComponent } from './order-components/order-card/order-card.component';
import { LoginComponent } from './login/login.component';
import { OrderNotificationsComponent } from './order-components/order-notifications/order-notifications.component';
import { WaiterPageComponent } from './pages/waiter-page/waiter-page.component';
import { CookPageComponent } from './pages/cook-page/cook-page.component';

import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSliderModule } from '@angular/material/slider';
import { MatInputModule } from '@angular/material/input';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { ToastrModule } from 'ngx-toastr';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HeaderComponent } from './header/header.component';
import { ItemsComponent } from './item-components/items/items.component';
import { EditDialog, ItemCardComponent } from './item-components/item-card/item-card.component';
import { ManagerPageComponent } from './pages/manager-page/manager-page/manager-page.component';
import {MatSelectModule} from '@angular/material/select';
import { CreateItemComponent } from './item-components/create-item/create-item.component';


@NgModule({
  declarations: [
    AppComponent,
    CanvasComponent,
    TableComponent,
    MapComponent,
    TableOrderComponent,
    OrderBoardComponent,
    OrderCardComponent,
    LoginComponent,
    OrderNotificationsComponent,
    WaiterPageComponent,
    CookPageComponent,
    NotFoundComponent,
    HeaderComponent,
    ItemsComponent,
    ItemCardComponent,
    ManagerPageComponent,
    EditDialog,
    CreateItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    CommonModule,
    MatTabsModule,
    MatSliderModule,
    MatInputModule,
    MatGridListModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatSelectModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
