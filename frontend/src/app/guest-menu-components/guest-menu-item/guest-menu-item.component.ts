import { Component, OnInit, Input } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';

@Component({
  selector: 'app-guest-menu-item',
  templateUrl: './guest-menu-item.component.html',
  styleUrls: ['./guest-menu-item.component.less'],
})
export class GuestMenuItemComponent implements OnInit {
  @Input() item: Item = {
    id: 0,
    name: '',
    category: { id: 0, name: '' },
    description: '',
    imageFileName: '',
    tags: [],
    inMenu: false,
    itemType: ItemType.FOOD,
    currentItemValue: {
      id: 0,
      purchasePrice: 0,
      sellingPrice: 0,
      fromDate: new Date(),
    },
    deleted: false,
  };

  constructor() {}

  ngOnInit(): void {}
}
