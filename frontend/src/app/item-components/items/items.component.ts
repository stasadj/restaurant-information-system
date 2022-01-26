import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
    selector: 'app-items',
    templateUrl: './items.component.html',
    styleUrls: ['./items.component.less']
})
export class ItemsComponent implements OnInit {

    items: Item[] = [];
    constructor(private itemService: ItemService) { }

    refreshData = () => {
        this.itemService.getItems().subscribe((res) => {
            this.items = res;
        });
    }

    ngOnInit(): void {
        console.log("here");
        this.itemService.getItems().subscribe((res) => {
            this.items = res;
        });

    }

}
