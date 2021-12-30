import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSliderModule } from '@angular/material/slider';
import { MatInputModule } from '@angular/material/input';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CanvasComponent } from './restaurant-map/canvas/canvas.component';
import { TableComponent } from './restaurant-map/table/table.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MapComponent } from './restaurant-map/map/map.component';

@NgModule({
  declarations: [AppComponent, CanvasComponent, TableComponent, MapComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    DragDropModule,
    MatTabsModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatInputModule,
    MatGridListModule,
    MatButtonModule,
    MatCardModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
