import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-item-sort',
  templateUrl: './item-sort.component.html',
  styleUrls: ['./item-sort.component.less'],
})
export class ItemSortComponent implements OnInit {
  @Input() onSortCriteriaChange: Function = () => {};

  constructor() {}

  ngOnInit(): void {}
}
