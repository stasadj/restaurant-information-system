import { Component, OnInit, Input } from '@angular/core';
import { Category } from 'src/app/model/Category';

@Component({
  selector: 'app-category-filter',
  templateUrl: './category-filter.component.html',
  styleUrls: ['./category-filter.component.less'],
})
export class CategoryFilterComponent implements OnInit {
  @Input() categories: Category[] = [];
  @Input() selectedCategoryId: number = 2;
  @Input() onCategoryChange: Function = () => {};

  constructor() {}

  ngOnInit(): void {}
}
