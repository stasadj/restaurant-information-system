import { Component, Input, OnInit, Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { ItemService } from 'src/app/services/item/item.service';
import { EditItemDialog } from '../edit-item-dialog/edit-item-dialog.component';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


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
        imageFileName: '',
        tags: [],
        inMenu: false,
        itemType: ItemType.FOOD,
        currentItemValue: { id: 0, purchasePrice: 0, sellingPrice: 0, fromDate: new Date() },
        deleted: false
    };

    @Input() onItemDeleted = () => {

    };

    public imgTagSrc : any;

    constructor(private itemService: ItemService, public dialog: MatDialog, private _sanitizer: DomSanitizer) { }


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
            this.onItemDeleted()
        });

    }

    ngOnInit(): void {
        this.imgTagSrc = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' 
                 + this.item.imageBase64);

    }

    openDialog(): void {
        const dialogRef = this.dialog.open(EditItemDialog, {
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
