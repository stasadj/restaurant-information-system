import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.less']
})
export class ItemComponent implements OnInit {

    items: Item[] = [];
    constructor(private itemService: ItemService) {}

  ngOnInit(): void {
    this.itemService.getItems().subscribe((res) => {
        console.log("this.items: " + this.items.length);
        console.log("res.body " + res.body);
        console.log(res);
        this.items = res.body as Item[];
        //this.items = <Item[]> res;
        //this.items = res.;
        //console.log(this.items);
        //this.items.forEach(item => console.log(item.name));
    });
    
  }

}
