import { ɵNoopAnimationStyleNormalizer } from '@angular/animations/browser';
import { Component, Input, OnInit } from '@angular/core';
import { create } from 'domain';
import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { ItemType } from 'src/app/model/ItemType';
import { CategoryService } from 'src/app/services/category/category.service';
import { ItemService } from 'src/app/services/item/item.service';
import { ToastrService } from 'ngx-toastr';
import { TagService } from 'src/app/services/tag/tag.service';
import { Tag } from 'src/app/model/Tag';
import { Form, FormGroup, FormBuilder } from '@angular/forms';

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
        imageFileName: '',
        tags: [],
        inMenu: false,
        itemType: ItemType.FOOD,
        currentItemValue: { id: 0, purchasePrice: 0, sellingPrice: 0, fromDate: new Date() },
        deleted: false
    };

    public fileName: string = "";
    public file: any;

    @Input() categories: Category[] = []
    @Input() tags: Tag[] = []

    @Input() checkBoxes: { tag: Tag; select: boolean }[] = [];
    public parentSelector: boolean = false;

    @Input() onItemCreated = () => {

    };

    constructor(private itemService: ItemService, private categoryService: CategoryService, private tagService: TagService, private toastr: ToastrService, private fb: FormBuilder) {

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



    ngOnInit(): void {

    }

    //method called when file upload changes
    onChange(event: any) {
        this.file = event.target.files[0];
    }


    onSaveClick(): void {

        let purchasePrice = this.newItem.currentItemValue.purchasePrice;
        let sellingPrice = this.newItem.currentItemValue.sellingPrice;

        if (this.newItem.name && this.newItem.description && this.newItem.category) {
            if (purchasePrice <= 0 || sellingPrice <= 0) {
                this.toastr.error("Invalid price input!");
                return;
            }

            //getting selected tags from checkboxes
            this.newItem.tags = this.checkBoxes.filter(cb => cb.select).map(cb => cb.tag);

            this.itemService.create(this.newItem, this.file).subscribe((res) => {
                this.onItemCreated();
                this.toastr.success('Item ' + res.name + " successfully created");

                //resetting form values after submit
                this.newItem = {
                    id: NaN,
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
                this.checkBoxes.forEach(cb => cb.select = false);
            })
        }
        else {
            this.toastr.error("Field inputs must not be left blank!");
        }
    }

    compareObjects(o1: any, o2: any): boolean {
        return o1.name === o2.name && o1.id === o2.id;
    }

}
