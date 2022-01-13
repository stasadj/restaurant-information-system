import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-table-order',
  templateUrl: './table-order.component.html',
  styleUrls: ['./table-order.component.less'],
})
export class TableOrderComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { tableId: number }) {}

  ngOnInit(): void {}
}
