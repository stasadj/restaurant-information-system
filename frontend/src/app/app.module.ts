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
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatChipsModule } from '@angular/material/chips';
import { ToastrModule } from 'ngx-toastr';
import { ChartModule } from 'angular2-chartjs';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HeaderComponent } from './header/header.component';

import { ItemsComponent } from './item-components/items/items.component';
import { ItemCardComponent } from './item-components/item-card/item-card.component';
import { EditItemDialog } from './item-components/edit-item-dialog/edit-item-dialog.component';
import { ManagerPageComponent } from './pages/manager-page/manager-page/manager-page.component';
import { MatSelectModule } from '@angular/material/select';
import { CreateItemComponent } from './item-components/create-item/create-item.component';
import { HandleErrorInterceptor } from './interceptors/error.interceptor';
import { BarmanPageComponent } from './pages/barman-page/barman-page.component';
import { ChartComponent } from './report/chart/chart.component';
import { ReportBoardComponent } from './report/report-board/report-board.component';
import { ChartDataPipe } from './pipes/chart-data/chart-data.pipe';
import { DateSerializePipe } from './pipes/date-serialize/date-serialize.pipe';
import { ReportSearchComponent } from './report/report-search/report-search.component';
import { CapitalizePipe } from './pipes/text/capitalize/capitalize.pipe';
import { CapitalizeWordsPipe } from './pipes/text/capitalize-words/capitalize-words.pipe';
import { RemoveSpecialCharsPipe } from './pipes/text/remove-special-chars/remove-special-chars.pipe';
import { ReportItemTableComponent } from './report/report-item-table/report-item-table.component';
import { FillItemNetincomePipe } from './pipes/table-data/fill-item-netincome/fill-item-netincome.pipe';
import { GuestPageComponent } from './pages/guest-page/guest-page.component';
import { CategoryFilterComponent } from './guest-menu-components/category-filter/category-filter.component';
import { ItemSearchComponent } from './guest-menu-components/item-search/item-search.component';
import { GuestMenuItemComponent } from './guest-menu-components/guest-menu-item/guest-menu-item.component';
import { GuestMenuComponent } from './guest-menu-components/guest-menu/guest-menu.component';
import { WaiterMenuComponent } from './menu-components/waiter-menu/waiter-menu.component';
import { UserProfileComponent } from './staff/user-profile/user-profile.component';
import { MatSortModule } from '@angular/material/sort';
import { AddTotalEntryPipe } from './pipes/table-data/add-total-entry/add-total-entry.pipe';

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
    BarmanPageComponent,
    NotFoundComponent,
    HeaderComponent,
    ItemsComponent,
    ItemCardComponent,
    ManagerPageComponent,
    EditItemDialog,
    CreateItemComponent,
    ChartComponent,
    ReportBoardComponent,
    ChartDataPipe,
    DateSerializePipe,
    ReportSearchComponent,
    CapitalizePipe,
    CapitalizeWordsPipe,
    RemoveSpecialCharsPipe,
    ReportItemTableComponent,
    FillItemNetincomePipe,
    WaiterMenuComponent,
    GuestPageComponent,
    CategoryFilterComponent,
    ItemSearchComponent,
    GuestMenuItemComponent,
    GuestMenuComponent,
    UserProfileComponent,
    AddTotalEntryPipe,
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
    MatSelectModule,
    ChartModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatSortModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HandleErrorInterceptor,
      multi: true,
    },
    ChartDataPipe,
    DateSerializePipe,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
