import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemSortComponent } from './item-sort.component';

describe('ItemSortComponent', () => {
  let component: ItemSortComponent;
  let fixture: ComponentFixture<ItemSortComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemSortComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
