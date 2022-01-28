import { Component, Input, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { ItemValue } from 'src/app/model/ItemValue';
import { CategoryService } from 'src/app/services/category/category.service';
import { ToastrService } from 'ngx-toastr';
import { Tag } from 'src/app/model/Tag';
import { TagService } from 'src/app/services/tag/tag.service';

@Component({
    selector: 'edit-item-dialog',
    templateUrl: 'edit-item-dialog.component.html',
})
export class EditItemDialog {

    public editItem: Item;
    public categories: Category[] = [];
    public tags: Tag[] = []
    public checkBoxes: { tag: Tag; select: boolean }[];
    parentSelector: boolean = false;


    constructor(
        public dialogRef: MatDialogRef<EditItemDialog>,
        private categoryService: CategoryService,
        private tagService: TagService,
        private toastr: ToastrService,

        @Inject(MAT_DIALOG_DATA) public data: Item = {
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
        }
    ) {

        this.editItem = JSON.parse(JSON.stringify(data)) // new object because we want to be able to compare old and new values
        this.checkBoxes = [];

        //fetching tags and setting checkbox values based on existing tags in item for edit
        this.tagService.getTags().subscribe(res => {
            this.tags = res;
            this.tags.forEach(tag => {
                let tagInItem = false;
                for (let itemTag of this.editItem.tags){
                    if (itemTag.id === tag.id){
                        tagInItem = true;
                        break;
                    }
                }
                this.checkBoxes.push({ tag: tag, select: tagInItem })
            });


        })
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

        if (this.editItem.name && this.editItem.description && this.editItem.category) {
            if (newPurchasePrice != oldPurchasePrice || newSellingPrice != oldSellingPrice) {
                if (newPurchasePrice <= 0 || newSellingPrice <= 0) {
                    this.toastr.error("Invalid price input!")
                    return;
                }

                //creating new ItemValue if price has changed
                let newValue: ItemValue = {
                    itemId: this.editItem.id,
                    purchasePrice: this.editItem.currentItemValue.purchasePrice,
                    sellingPrice: this.editItem.currentItemValue.sellingPrice,

                }
                this.editItem.currentItemValue = newValue;
            }

            //getting selected tags from checkboxes
            this.editItem.tags = this.checkBoxes.filter(cb => cb.select).map(cb => cb.tag);
            this.dialogRef.close(this.editItem);
        }
        else {
            this.toastr.error("Form fields must not be blank!")
        }


    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
    }

    onChangeTag($event: any) {
        const id = $event.target.value;
        const isChecked = $event.target.checked;

        this.checkBoxes = this.checkBoxes.map((d) => {
            if (d.tag.id == id) {
                d.select = isChecked;
                this.parentSelector = false;
                return d;
            }
            if (id == -1) {
                d.select = this.parentSelector;
                return d;
            }
            return d;
        });
    }
}
