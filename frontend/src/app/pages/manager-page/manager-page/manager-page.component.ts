import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
    selector: 'app-manager-page',
    templateUrl: './manager-page.component.html',
    styleUrls: ['./manager-page.component.less']
})
export class ManagerPageComponent implements OnInit {

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
