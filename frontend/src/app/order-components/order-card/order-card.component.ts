import { Component, Input, OnInit } from '@angular/core';
import { Order } from 'src/app/model/Order';

@Component({
  selector: 'app-order-card',
  templateUrl: './order-card.component.html',
  styleUrls: ['./order-card.component.less'],
})
export class OrderCardComponent implements OnInit {
  @Input() order: Order = {
    id: 0,
    createdAt: new Date(),
    tableId: 0,
    orderItems: [
      {
        id: 0,
        amount: 0,
        orderId: 0,
        orderStatus: 'PENDING',
        itemId: 0,
        item: { name: '', type: 'FOOD' },
      },
    ],
    waiterId: 0,
  };
  @Input() selectable = false;
  @Input() isBarman = false;
  items: any[] = [];

  constructor() {}

  ngOnInit(): void {}
}
