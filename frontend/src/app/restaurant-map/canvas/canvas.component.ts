import { Component, OnInit } from '@angular/core';
import { RestaurantTable } from '../RestaurantTable';

@Component({
  selector: 'app-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.less'],
})
export class CanvasComponent implements OnInit {
  tables: RestaurantTable[] = [
    {
      id: '1',
      rotateValue: 0,
      size: { w: 150, h: 50 },
      radius: 0,
      position: { x: 0, y: 0 },
      status: 'a',
    },
    {
      id: '2',
      rotateValue: 50,
      size: { w: 50, h: 50 },
      radius: 0,
      position: { x: 100, y: 0 },
      status: 'a',
    },
    {
      id: '3',
      rotateValue: 100,
      size: { w: 120, h: 50 },
      radius: 0,
      position: { x: 50, y: 50 },
      status: 'b',
    },
  ];
  currentTable?: RestaurantTable;

  constructor() {}

  ngOnInit(): void {}
  onSave() {
    console.log(this.tables);
  }
  onTableClick(id: string) {
    this.currentTable = this.tables.filter((x) => x.id === id)[0];
    console.log(this.currentTable);
  }
  onAdd() {
    this.tables.push({
      id: `${this.tables.length + 1}`,
      rotateValue: 0,
      size: { w: 100, h: 50 },
      radius: 20,
      position: { x: 0, y: -this.tables.length * 100 },
      status: 'b',
    });
  }
}
