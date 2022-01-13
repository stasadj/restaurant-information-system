import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Order } from 'src/app/model/Order';
import { OrderItem } from 'src/app/model/OrderItem';
import { OrderService } from 'src/app/services/order/order.service';

interface OrderBoard {
  pending: Order[];
  inProgressMy: Order[];
  inProgressOther: Order[];
  declined: Order[];
  ready: Order[];
}

@Component({
  selector: 'app-order-board',
  templateUrl: './order-board.component.html',
  styleUrls: ['./order-board.component.less'],
})
export class OrderBoardComponent implements OnInit, OnDestroy {
  isBarman = true;
  orderBoard: OrderBoard = {
    pending: [],
    inProgressMy: [],
    inProgressOther: [],
    declined: [],
    ready: [],
  };
  userId = 0;
  orders: Order[] = [];

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.userId = Number(localStorage.getItem('userId'));
    this.isBarman = localStorage.getItem('role') === 'barman';

    this.orderService.connect();
    this.orderService.getAll().subscribe((d) => {
      this.orders = d ?? [];
      this.arrange();
    });
    this.orderService.orderSubject.subscribe((o) => this.receiveOrder(o));
    this.orderService.orderItemsSubject.subscribe((o) =>
      this.receiveOrderItems(o)
    );
    this.orderService.cancelledItemsSubject.subscribe((o) =>
      this.receiveCancelled(o)
    );
  }
  ngOnDestroy(): void {
    this.orderService.disconnect();
    this.orderService.orderSubject.unsubscribe();
    this.orderService.orderItemsSubject.unsubscribe();
    this.orderService.cancelledItemsSubject.unsubscribe();
  }

  receiveOrder(order: Order) {
    let i = this.orders.findIndex((o) => o.id === order.id);
    if (i === -1) this.orders.push(order);
    else this.orders[i] = order;
    this.arrange();
  }

  receiveOrderItems(orderItems: OrderItem[]) {
    for (let orderItem of orderItems) {
      let i = this.orders.findIndex((o) => o.id === orderItem.orderId);
      if (i === -1) continue;
      let ii = this.orders[i].orderItems.findIndex(
        (oi) => oi.id === orderItem.id
      );
      if (ii === -1) this.orders[i].orderItems.push(orderItem);
      else this.orders[i].orderItems[ii] = orderItem;
    }
    if (orderItems.length > 0) this.arrange();
  }

  receiveCancelled(itemsIds: number[]) {
    for (let cancelledId of itemsIds)
      for (let order of this.orders) {
        let i = order.orderItems.findIndex((oi) => oi.id == cancelledId);
        if (i !== -1) {
          order.orderItems.splice(i, 1);
          break;
        }
      }
    if (itemsIds.length > 0) this.arrange();
  }

  arrange() {
    let arranged: OrderBoard = {
      pending: [],
      inProgressMy: [],
      inProgressOther: [],
      declined: [],
      ready: [],
    };

    let addToColumn = (column: Order[], order: Order, item: OrderItem) => {
      let i = column.findIndex((o) => o.id === item.orderId);
      if (i === -1) {
        let o = { ...order };
        o.orderItems = [item];
        column.push(o);
      } else {
        column[i].orderItems.push(item);
      }
    };

    for (let order of this.orders)
      for (let orderItem of order.orderItems) {
        switch (orderItem.orderStatus) {
          case 'PENDING':
            addToColumn(arranged.pending, order, orderItem);
            break;
          case 'IN_PROGRESS':
            addToColumn(
              orderItem.barmanId === this.userId ||
                orderItem.cookId === this.userId
                ? arranged.inProgressMy
                : arranged.inProgressOther,
              order,
              orderItem
            );
            break;
          case 'READY':
            addToColumn(arranged.ready, order, orderItem);
            break;
          case 'DECLINED':
            addToColumn(arranged.declined, order, orderItem);
            break;
        }
      }
    this.orderBoard = arranged;
  }
}