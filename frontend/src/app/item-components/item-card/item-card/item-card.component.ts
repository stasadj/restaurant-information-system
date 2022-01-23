import { Component, Input, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { ItemService } from 'src/app/services/item/item.service';

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
  constructor(private itemService: ItemService) { }

  onAddToMenu(){
      this.itemService.addToMenu(this.item).subscribe((res) => {
          this.item = res;
      });

  }

  onRemoveFromMenu(){
    this.itemService.removeFromMenu(this.item).subscribe((res) => {
        this.item = res;
    });

}

  ngOnInit(): void {
  }

}
