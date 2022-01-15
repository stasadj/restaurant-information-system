import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TableService } from 'src/app/services/table/table.service';
import { RoomOrganization } from '../../model/RoomOrganization';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.less'],
})
export class MapComponent implements OnInit {
  rooms: RoomOrganization[] = [{ id: 'Room 0', tables: [] }];

  @Output() tableClickEvent = new EventEmitter<number>();

  constructor(private tableService: TableService) {}

  ngOnInit(): void {
    this.tableService.getRooms().subscribe((data) => (this.rooms = data.rooms));
  }

  onTableClick(id: number) {
    this.tableClickEvent.emit(id);
  }
}
