import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.less'],
})
export class ItemSearchComponent implements OnInit {
  @Input() searchInput: string = '';
  @Input() onSearch: Function = () => {};
  @Input() onSortCriteriaChange: Function = () => {};

  constructor() {}

  ngOnInit(): void {}
}
