import { Component, Input, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA, MatDialogClose } from '@angular/material/dialog';

import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
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
            data: { ... this.item },
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.itemService.edit(result).subscribe(res => {
                    this.item = res;
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
        private categoryService : CategoryService,

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
        this.editItem = { ...data }
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
        if (this.editItem.name && this.editItem.description && this.editItem.category) {
            this.dialogRef.close(this.editItem);
            return;
        }

        console.log("invalid form data!");

    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
      }
}
