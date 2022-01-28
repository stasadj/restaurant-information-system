import { ɵNoopAnimationStyleNormalizer } from '@angular/animations/browser';
import { Component, Input, OnInit } from '@angular/core';
import { create } from 'domain';
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
        name: 'a',
        category: { id: 1, name: "appetizer" },
        description: 'a',
        imageFileName: '',
        tags: [],
        inMenu: false,
        itemType: ItemType.FOOD,
        currentItemValue: { id: 0, purchasePrice: 10, sellingPrice: 10, fromDate: new Date() },
        deleted: false
    };

    public fileName : any;
    public file : any;

    public categories: Category[] = []

    @Input() onItemCreated = () => {

    };

    constructor(private itemService: ItemService, private categoryService: CategoryService) { }



    ngOnInit(): void {
        this.categoryService.getCategories().subscribe(res => {
            this.categories = res;
        })
    }

    onChange(event: any) {
        this.file = event.target.files[0];
    }
  

    onSaveClick(): void {

        let purchasePrice = this.newItem.currentItemValue.purchasePrice;
        let sellingPrice = this.newItem.currentItemValue.sellingPrice;

        if (this.newItem.name && this.newItem.description && this.newItem.category && purchasePrice && sellingPrice) {
            if (purchasePrice <= 0 || sellingPrice <= 0) {
                console.log("invalid price data!");
                return;
            }

            this.itemService.create(this.newItem, this.file).subscribe((res) => {
                this.onItemCreated();
            })

        }
        else {
            console.log("missing/invalid form data!");
        }


    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
    }

}
