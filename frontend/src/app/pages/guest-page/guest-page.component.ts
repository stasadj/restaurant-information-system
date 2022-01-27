import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
  selector: 'app-guest-page',
  templateUrl: './guest-page.component.html',
  styleUrls: ['./guest-page.component.less'],
})
export class GuestPageComponent implements OnInit {
    public items: Item[] = [];
    constructor(private itemService: ItemService) { }

    fetchData = () => {
        this.itemService.getItems().subscribe((res) => {
            this.items = res;
        });
    }

    ngOnInit(): void {
        this.itemService.getItems().subscribe((res) => {
            this.items = res;
        })
    }
}