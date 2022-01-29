import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Category } from 'src/app/model/Category';
import { Item } from 'src/app/model/Item';
import { OrderItemInfo } from 'src/app/model/OrderItemInfo';
import { CategoryService } from 'src/app/services/category/category.service';
import { ItemService } from 'src/app/services/item/item.service';

@Component({
  selector: 'app-waiter-menu',
  templateUrl: './waiter-menu.component.html',
  styleUrls: ['./waiter-menu.component.less'],
})
export class WaiterMenuComponent implements OnInit {
  @Input() isBarOrder?: boolean = false;
  @Output() addEvent = new EventEmitter<OrderItemInfo>();
  categories: Category[] = [];
  menuItems: Item[] = [];
  selectedCategoryId: number[] = [];
  title: string = '';
  selectedItem?: Item;
  displayedColumns: string[] = ['name', 'price'];
  amount: number = 0;
  imgTagSrc: any;

  constructor(
    private categoryService: CategoryService,
    private itemService: ItemService,
    private _sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    if (this.isBarOrder)
      this.categoryService
        .getDrinkCategories()
        .subscribe((d) => (this.categories = d));
    else
      this.categoryService
        .getCategories()
        .subscribe((d) => (this.categories = d));
  }

  onCategoryChanged() {
    this.title =
      this.categories.find((c) => c.id === this.selectedCategoryId[0])?.name ??
      '';
    this.itemService
      .getItemsByCategory(this.selectedCategoryId[0])
      .subscribe((d) => {
        this.menuItems = d;
      });
  }

  onSelectItem(item: Item) {
    this.selectedItem = item;
    this.imgTagSrc = this._sanitizer.bypassSecurityTrustResourceUrl(
      'data:image/png;base64,' + this.selectedItem.imageBase64
    );
  }

  onAdd() {
    if (this.selectedItem)
      this.addEvent.emit({
        id: this.selectedItem.id,
        name: this.selectedItem.name,
        amount: this.amount,
        price: this.selectedItem.currentItemValue.sellingPrice,
      });
    this.amount = 0;
  }

  plus() {
    this.amount += this.amount > 9999 ? 0 : 1;
  }

  minus() {
    this.amount -= this.amount < 1 ? 0 : 1;
  }
}
