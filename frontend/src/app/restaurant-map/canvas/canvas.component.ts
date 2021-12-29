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
    console.log(this.rooms);
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
  onAdd() {
    this.rooms[this.currentRoomIndex].tables.push({
      id: `${Math.floor(Math.random() * 1000)}`,
      rotateValue: 0,
      size: { w: 100, h: 50 },
      radius: 20,
      position: { x: 0, y: 0 },
      status: 'b',
    });
  }
  formatDegrees(value: number) {
    return value + 'º';
  }
  formatPx(value: number) {
    return value + 'px';
  }
}
