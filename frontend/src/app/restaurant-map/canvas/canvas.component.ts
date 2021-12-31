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

  onAddTable(copy: boolean) {
    let newTable =
      copy && this.currentTable
        ? {
            id: `${Math.floor(Math.random() * 1000)}`,
            rotateValue: this.currentTable.rotateValue,
            size: { ...this.currentTable.size },
            radius: this.currentTable.radius,
            position: {
              x: this.currentTable.position.x + 10,
              y: this.currentTable.position.y + 10,
            },
            status: '',
          }
        : {
            id: `${Math.floor(Math.random() * 1000)}`,
            rotateValue: 0,
            size: { w: 100, h: 50 },
            radius: 20,
            position: { x: 0, y: 0 },
          };
    this.rooms[this.currentRoomIndex].tables.push(newTable);
    this.currentTable = newTable;
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

  rotate90() {
    if (!this.currentTable) return;
    let h = this.currentTable.size.h;
    let w = this.currentTable.size.w;
    let pos = this.currentTable.position;
    this.currentTable.position = {
      x: pos.x + (w - h) / 2,
      y: pos.y - (w - h) / 2,
    };
    this.currentTable.size.h = this.currentTable.size.w;
    this.currentTable.size.w = h;
  }
}
