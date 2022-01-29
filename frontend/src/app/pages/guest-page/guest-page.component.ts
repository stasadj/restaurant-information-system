import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/model/Item';
import { Category } from 'src/app/model/Category';
import { ItemService } from 'src/app/services/item/item.service';
import { CategoryService } from 'src/app/services/category/category.service';

@Component({
  selector: 'app-guest-page',
  templateUrl: './guest-page.component.html',
  styleUrls: ['./guest-page.component.less'],
})
export class GuestPageComponent implements OnInit {
  public items: Item[] = [];
  public categories: Category[] = [];
  public selectedCategoryId: number = 0;
  public searchInput: string = '';
  public sortCriteria: number = 0;
  constructor(
    private itemService: ItemService,
    private categoryService: CategoryService
  ) {}

  getMenuItems = () => {
    this.itemService
      .searchMenuItems(
        this.selectedCategoryId,
        this.searchInput,
        this.sortCriteria
      )
      .subscribe((res) => {
        this.items = res;
      });
  };

  ngOnInit(): void {
    this.itemService
      .searchMenuItems(
        this.selectedCategoryId,
        this.searchInput,
        this.sortCriteria
      )
      .subscribe((res) => {
        this.items = res;
      });
    this.categoryService.getCategories().subscribe((res) => {
      this.categories = res;
    });
  }

  handleCategoryChange = (id: number) => {
    this.selectedCategoryId = id;
    this.searchInput = '';
    this.getMenuItems();
  };

  handleSearchButtonClick = (searchInput: string) => {
    this.searchInput = searchInput;
    this.getMenuItems();
  };

  handleSortCriteriaChange = (sortCriteria: number) => {
    this.sortCriteria = sortCriteria;
    this.getMenuItems();
  };
}
