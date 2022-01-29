import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { Tag } from 'src/app/model/Tag';
import { CategoryService } from 'src/app/services/category/category.service';
import { ItemService } from 'src/app/services/item/item.service';
import { TagService } from 'src/app/services/tag/tag.service';

@Component({
    selector: 'app-manager-page',
    templateUrl: './manager-page.component.html',
    styleUrls: ['./manager-page.component.less']
})
export class ManagerPageComponent implements OnInit {

    public items: Item[] = [];
    public tags: Tag[] = [];
    public categories: Category[] = [];

    constructor(private itemService: ItemService, private tagsService: TagService, private categoryService: CategoryService) { }

    fetchData = () => {
        this.itemService.getItems().subscribe((res) => {
            this.items = res;
        });
        this.tagsService.getTags().subscribe((res) => {
            this.tags = res;
        });
        this.categoryService.getCategories().subscribe((res) => {
            this.categories = res;
        });
    }

    ngOnInit(): void {
        this.fetchData();
    }

}
