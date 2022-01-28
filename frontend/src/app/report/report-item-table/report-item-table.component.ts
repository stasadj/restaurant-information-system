import { Component, Input, OnInit } from '@angular/core';
import { ItemDatapoint } from 'src/app/model/ItemDatapoint';

@Component({
  selector: 'app-report-item-table',
  templateUrl: './report-item-table.component.html',
  styleUrls: ['./report-item-table.component.less']
})
export class ReportItemTableComponent implements OnInit {

  @Input() data: ItemDatapoint[] = [];
  displayedColumns: string[] = ['name', 'quantity', 'expenses', 'grossIncome', 'netIncome'];

  constructor() { }

  ngOnInit(): void {
  }

}
