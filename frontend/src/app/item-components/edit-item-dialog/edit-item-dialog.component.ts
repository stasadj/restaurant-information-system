import { Component, Input, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { ItemValue } from 'src/app/model/ItemValue';
import { CategoryService } from 'src/app/services/category/category.service';


@Component({
    selector: 'edit-item-dialog',
    templateUrl: 'edit-item-dialog.component.html',
})
export class EditItemDialog {

    public editItem: Item;
    public categories: Category[] = [];

    constructor(
        public dialogRef: MatDialogRef<EditItemDialog>,
        private categoryService: CategoryService,

        @Inject(MAT_DIALOG_DATA) public data: Item = {
            id: 0,
            name: '',
            category: { id: 0, name: "" },
            description: '',
            imageURL: '',
            tags: [],
            inMenu: false,
            itemType: ItemType.FOOD,
            currentItemValue: { id: 0, purchasePrice: 0, sellingPrice: 0, fromDate: new Date() },
            deleted: false
        }
    ) {
        this.editItem = JSON.parse(JSON.stringify(data)) // new object because we want to be able to compare old and new values
    }

    ngOnInit(): void {
        this.categoryService.getCategories().subscribe(res => {
            this.categories = res;
        })
    }

    onCancelClick(): void {
        this.dialogRef.close();
    }

    onSaveClick(): void {

        let newPurchasePrice = this.editItem.currentItemValue.purchasePrice;
        let newSellingPrice = this.editItem.currentItemValue.sellingPrice;

        let oldPurchasePrice = this.data.currentItemValue.purchasePrice;
        let oldSellingPrice = this.data.currentItemValue.sellingPrice;

        if (this.editItem.name && this.editItem.description && this.editItem.category && newPurchasePrice && newSellingPrice) {
            if (newPurchasePrice != oldPurchasePrice || newSellingPrice != oldSellingPrice) {
                console.log("creating new item value")
                if (newPurchasePrice <= 0 || newSellingPrice <= 0) {
                    console.log("invalid price data!");
                    return;
                }

                let newValue: ItemValue = {
                    itemId: this.editItem.id,
                    purchasePrice: this.editItem.currentItemValue.purchasePrice,
                    sellingPrice: this.editItem.currentItemValue.sellingPrice,

                }
                this.editItem.currentItemValue = newValue;
            }
            this.dialogRef.close(this.editItem);
        }
        else {
            console.log("missing/invalid form data!");
        }


    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
    }
}
