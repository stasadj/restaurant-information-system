import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderNotificationsComponent } from './order-notifications.component';

describe('OrderNotificationsComponent', () => {
  let component: OrderNotificationsComponent;
  let fixture: ComponentFixture<OrderNotificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderNotificationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
