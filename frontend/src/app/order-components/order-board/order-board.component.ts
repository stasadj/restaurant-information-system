import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Order } from 'src/app/model/Order';
import { OrderItem } from 'src/app/model/OrderItem';
import { NotificationService } from 'src/app/services/notification/notification.service';
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
  orderBoard?: OrderBoard;
  userId = 0;
  orders: Order[] = [];
  private subscriptions = new Subscription();

  constructor(
    private orderService: OrderService,
    private notifiationService: NotificationService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.userId = Number(localStorage.getItem('userId'));
    this.isBarman = localStorage.getItem('role') === 'barman';

    this.orderService.connect();
    this.notifiationService.connect();
    let c = this.orderService.connected$.subscribe(() =>
      this.orderService.getAll().subscribe((d) => {
        this.orders = d ?? [];
        this.arrange();
      })
    );
    this.subscriptions.add(c);
    this.subscriptions.add(
      this.orderService.orderSubject.subscribe((o) => this.receiveOrder(o))
    );
    this.subscriptions.add(
      this.orderService.orderItemsSubject.subscribe((o) =>
        this.receiveOrderItems(o)
      )
    );
    this.subscriptions.add(
      this.orderService.cancelledItemsSubject.subscribe((o) =>
        this.receiveCancelled(o)
      )
    );
    this.subscriptions.add(
      this.notifiationService.finalizedSubject.subscribe((id) =>
        this.receiveFinalized(id)
      )
    );
    this.subscriptions.add(
      this.notifiationService.notificationSubject.subscribe((n) =>
        this.toastr.info(n.text)
      )
    );
  }
  ngOnDestroy(): void {
    this.notifiationService.disconnect();
    this.orderService.disconnect();
    this.subscriptions.unsubscribe();
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

  receiveFinalized(id: number) {
    let i = this.orders.findIndex((o) => o.id === id);
    if (i !== -1) this.orders.splice(i, 1);
    i = this.orderBoard?.ready.findIndex((o) => o.id === id) ?? -1;
    if (i !== -1) this.orderBoard?.ready.splice(i, 1);
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
