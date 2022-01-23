import { Component, Input, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA, MatDialogClose } from '@angular/material/dialog';
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
                console.log(this.item);
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

    constructor(
        public dialogRef: MatDialogRef<EditDialog>,

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

    onCancelClick(): void {
        this.dialogRef.close();
    }

    onSaveClick(): void {
        if (this.editItem.name && this.editItem.description) {
            this.dialogRef.close(this.editItem);
            return;
        }

        console.log("invalid form data!");

    }
}
