import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { CategoryService } from 'src/app/services/category/category.service';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
    selector: 'app-create-item',
    templateUrl: './create-item.component.html',
    styleUrls: ['./create-item.component.less']
})
export class CreateItemComponent implements OnInit {

    public newItem: Item = {
        id: NaN,
        name: '',
        category: { id: 0, name: "" },
        description: '',
        imageURL: '',
        tags: [],
        inMenu: false,
        itemType: ItemType.FOOD,
        currentItemValue: { id: 0, purchasePrice: 0, sellingPrice: 0, fromDate: new Date() },
        deleted: false
    };

    public categories : Category[] = []


    constructor(private itemService : ItemService, private categoryService: CategoryService) { }

    ngOnInit(): void {
        this.categoryService.getCategories().subscribe(res => {
            this.categories = res;
        })    }

    onSaveClick(): void {

        let purchasePrice = this.newItem.currentItemValue.purchasePrice;
        let sellingPrice = this.newItem.currentItemValue.sellingPrice;

        if (this.newItem.name && this.newItem.description && this.newItem.category && purchasePrice && sellingPrice) {
            if (purchasePrice <= 0 || sellingPrice <= 0) {
                console.log("invalid price data!");
                return;
            }

            this.itemService.create(this.newItem).subscribe(res => {
                console.log(res);
            });

            
            
        }
        else {
            console.log("missing/invalid form data!");
        }


    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
    }

}
