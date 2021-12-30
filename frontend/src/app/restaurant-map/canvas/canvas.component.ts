import { Component, OnInit } from '@angular/core';
import { TableService } from 'src/app/services/table.service';
import { RestaurantTable } from '../RestaurantTable';

@Component({
  selector: 'app-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.less'],
})
export class CanvasComponent implements OnInit {
  rooms: { id: string; tables: RestaurantTable[] }[] =
    this.tableService.getRooms();

  currentTable?: RestaurantTable;
  currentRoomIndex: number = 0;

  constructor(private tableService: TableService) {}

  ngOnInit(): void {}

  onSave() {
    this.tableService.saveRooms(this.rooms);
  }

  onTableClick(id: string) {
    this.currentTable = this.rooms[this.currentRoomIndex].tables.find(
      (x) => x.id === id
    );
    console.log(this.currentTable);
  }

  tabChanged($event: any) {
    this.currentRoomIndex = $event.index;
  }

  onAddTable() {
    this.rooms[this.currentRoomIndex].tables.push({
      id: `${Math.floor(Math.random() * 1000)}`,
      rotateValue: 0,
      size: { w: 100, h: 50 },
      radius: 20,
      position: { x: 0, y: 0 },
      status: 'b',
    });
  }

  onDeleteTable() {
    let i = this.rooms[this.currentRoomIndex].tables.findIndex(
      (x) => x.id === this.currentTable?.id
    );
    this.rooms[this.currentRoomIndex].tables.splice(i, 1);
    this.currentTable = undefined;
  }

  onAddRoom() {
    this.rooms.push({
      id: `Room ${this.rooms.length + 1}`,
      tables: [],
    });
  }

  onDeleteRoom() {
    this.rooms.splice(this.currentRoomIndex, 1);
    if (this.rooms.length === 0) this.onAddRoom();
    this.currentRoomIndex = 0;
  }

  formatDegrees = (value: number) => value + 'º';
  formatPx = (value: number) => value + 'px';
}
