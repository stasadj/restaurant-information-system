import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Order } from 'src/app/model/Order';
import { OrderItem } from 'src/app/model/OrderItem';
import { OrderItemService } from 'src/app/services/order-item/order-item.service';
import { OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-table-order',
  templateUrl: './table-order.component.html',
  styleUrls: ['./table-order.component.less'],
})
export class TableOrderComponent implements OnInit {
  order?: Order;
  noOrder: boolean = false;
  edited: boolean = false;
  total: number = 0;
  canBeFinalized: boolean = false;
  isBarman: boolean = true;

  @ViewChild(MatTable) table?: MatTable<OrderItem>;

  displayedColumns: string[] = [
    'id',
    'name',
    'amount',
    'price',
    'status',
    'action',
  ];

  dummyMenu = [
    { id: 1, name: 'Spaghetti carbonara', price: 1000, a: 0 },
    { id: 2, name: 'Chicken tikka masala', price: 1200, a: 0 },
    { id: 3, name: 'Mushroom risotto', price: 1300, a: 0 },
    { id: 4, name: 'Peach ice tea', price: 130, a: 0 },
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { tableId: number },
    private orderService: OrderService,
    private orderItemService: OrderItemService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.isBarman = localStorage.getItem('role') === 'barman';
    this.orderService.getForTable(this.data.tableId).subscribe((d) => {
      this.order = d;
      this.noOrder = !d;
      this.calcTotal();
    });
  }

  initOrder() {
    this.order = {
      tableId: this.data.tableId,
      waiterId: Number(localStorage.getItem('userId')),
      orderItems: [],
    };
    this.noOrder = false;
  }

  addToOrder(orderItem: {
    id: number;
    name: string;
    amount: number;
    price: number;
  }) {
    if (!orderItem.amount) return;
    if (!this.order) this.initOrder();
    let existingItem = this.order?.orderItems.find(
      (oi) =>
        oi.itemId === orderItem.id &&
        (!oi.orderStatus || oi.orderStatus === 'PENDING')
    );
    if (existingItem) existingItem.amount += orderItem.amount;
    else
      this.order?.orderItems.push({
        itemId: orderItem.id,
        amount: orderItem.amount,
        item: { name: orderItem.name, price: orderItem.price },
      });
    this.table?.renderRows();
    this.edited = true;
    this.calcTotal();
  }

  cancelItem(orderItemId: number, itemId: number) {
    let i =
      this.order?.orderItems.findIndex(
        (oi) => oi.id === orderItemId && oi.itemId === itemId
      ) ?? -1;
    if (i === -1) return;
    let oi = this.order?.orderItems[i];
    if (oi?.orderStatus === 'IN_PROGRESS' || oi?.orderStatus === 'READY')
      return;
    if (oi?.id) {
      this.orderItemService.cancelOrderItem([oi.id]).subscribe((d) => {
        if (d.message) this.toastr.warning(d.message);
        else {
          this.order?.orderItems.splice(i, 1);
          this.table?.renderRows();
          this.calcTotal();
        }
      });
    } else {
      this.order?.orderItems.splice(i, 1);
      this.table?.renderRows();
      this.calcTotal();
    }
  }

  createOrder() {
    if (this.order && this.order.orderItems.length > 0) {
      if (this.isBarman)
        this.orderService.createBarOrder(this.order).subscribe((o) => {
          this.order = o;
          this.calcTotal();
          this.toastr.success('Bar order is created');
        });
      else
        this.orderService.createOrder(this.order).subscribe((o) => {
          this.order = o;
          this.calcTotal();
          this.toastr.success('Order is created');
        });
    }
    this.edited = false;
  }

  updateOrder() {
    if (this.order && this.order.id && this.order.orderItems.length > 0)
      this.orderService.editOrder(this.order).subscribe((o) => {
        this.order = o;
        this.calcTotal();
        this.toastr.success('Order is updated');
      });
    this.edited = false;
  }

  finalizeOrder() {
    if (
      this.order?.id &&
      this.order.orderItems.every((oi) => oi.orderStatus === 'READY')
    )
      this.orderService.finalizeOrder(this.order.id).subscribe((records) => {
        console.log(records);
        this.order = undefined;
        this.noOrder = true;
      });
  }

  calcTotal(): void {
    this.total =
      this.order?.orderItems
        .map((oi) => oi.amount * (oi.item.price ?? 0))
        .reduce((acc, value) => acc + value, 0) ?? 0;
    this.canBeFinalized =
      !!this.order?.id &&
      this.order.orderItems.every((oi) => oi.orderStatus === 'READY') &&
      !(!!this.order.waiterId && this.isBarman);
  }
}
