import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RestaurantTable } from '../RestaurantTable';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.less'],
})
export class TableComponent implements OnInit {
  @Input() table: RestaurantTable = {
    id: '#0',
    rotateValue: 0,
    size: { w: 100, h: 100 },
    radius: 0,
    position: { x: 0, y: -100 },
    status: '',
  };
  @Input() disableDrag: boolean = true;

  @Output() clickEvent = new EventEmitter<string>();

  constructor() {}

  ngOnInit(): void {}
  onClick() {
    this.clickEvent.emit(this.table.id);
  }
  dragEnd($event: any) {
    this.table.position = $event.source.getFreeDragPosition();
    console.log($event.source.getFreeDragPosition());
  }
}
