import { Component, OnInit } from '@angular/core';
import { TableService } from 'src/app/services/table.service';
import { RestaurantTable } from '../RestaurantTable';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.less'],
})
export class MapComponent implements OnInit {
  rooms: { id: string; tables: RestaurantTable[] }[] =
    this.tableService.getRooms();

  constructor(private tableService: TableService) {}

  ngOnInit(): void {}

  onTableClick(id: string) {
    console.log(id);
    //todo: order view
  }
}
