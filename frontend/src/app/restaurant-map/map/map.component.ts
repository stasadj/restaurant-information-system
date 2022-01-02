import { Component, OnInit } from '@angular/core';
import { TableService } from 'src/app/services/table.service';
import { RoomOrganization } from '../RoomOrganization';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.less'],
})
export class MapComponent implements OnInit {
  rooms: RoomOrganization[] = [{ id: 'Room1', tables: [] }];

  constructor(private tableService: TableService) {}

  ngOnInit(): void {
    this.tableService.getRooms().subscribe((data) => (this.rooms = data.rooms));
  }

  onTableClick(id: number) {
    alert('Order for table ' + id);
    //todo: order view
  }
}
