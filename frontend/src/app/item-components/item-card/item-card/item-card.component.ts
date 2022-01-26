import { Component, Input, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA, MatDialogClose } from '@angular/material/dialog';

import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { ItemValue } from 'src/app/model/ItemValue';

import { CategoryService } from 'src/app/services/category/category.service';
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
        category: { id: 0, name: "" },
        description: '',
        imageURL: '',
        tags: [],
        inMenu: false,
        itemType: ItemType.FOOD,
        currentItemValue: { id: 0, purchasePrice: 0, sellingPrice: 0, fromDate: new Date() },
        deleted: false
    };

    @Input() refreshDataCallback = () => {

    };

    constructor(private itemService: ItemService, public dialog: MatDialog) { }


    onAddToMenu() {
        this.itemService.addToMenu(this.item).subscribe((res) => {
            this.item = res;
        });

    }

    onRemoveFromMenu() {
        this.itemService.removeFromMenu(this.item).subscribe((res) => {
            this.item = res;
        });

    }

    onDelete() {
        //todo add Are you sure? modal window
        this.itemService.delete(this.item).subscribe((res) => {
            this.refreshDataCallback()
        });

    }

    ngOnInit(): void {
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(EditDialog, {
            data: JSON.parse(JSON.stringify(this.item))
        });

        dialogRef.afterClosed().subscribe(itemForSave => {
            if (itemForSave) {
                this.itemService.edit(itemForSave).subscribe(savedItem => {
                    this.item = savedItem;

                    if (itemForSave.currentItemValue.itemId) {
                        //changing price only if itemId is set (Look at ChangeItemDTO and ItemValueDTO difference)
                        console.log("changing price")
                        this.itemService.changeItemPrice(itemForSave.currentItemValue).subscribe(newValue => {
                            this.item.currentItemValue = newValue;
                            console.log(this.item);
                        })
                    }


                })
            }
        });
    }



}

@Component({
    selector: 'edit-dialog',
    templateUrl: 'edit-dialog.html',
})
export class EditDialog {

    public editItem: Item;
    public categories: Category[] = [];

    constructor(
        public dialogRef: MatDialogRef<EditDialog>,
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
        this.categoryService.getItems().subscribe(res => {
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
            console.log("missing form data!");
        }


    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
    }
}
