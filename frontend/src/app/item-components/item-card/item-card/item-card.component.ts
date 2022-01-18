import { Component, Input, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';

@Component({
  selector: 'app-item-card',
  templateUrl: './item-card.component.html',
  styleUrls: ['./item-card.component.less']
})
export class ItemCardComponent implements OnInit {

    @Input() item: Item = {
        id: 0,
        name: '',
        category: {id:0, name:""},
        description: '',
        imageURL: '',
        tags: [],
        inMenu: false,
        itemType: ItemType.FOOD,
        currentItemValue: {id:0, purchasePrice:0, sellingPrice:0, fromDate: new Date()},
        deleted: false
    };
  constructor() { }

  ngOnInit(): void {
  }

}
