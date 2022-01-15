import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderBoardComponent } from './order-board.component';

describe('OrderBoardComponent', () => {
  let component: OrderBoardComponent;
  let fixture: ComponentFixture<OrderBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
