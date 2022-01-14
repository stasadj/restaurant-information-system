import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { Order } from 'src/app/model/Order';
import { OrderItem } from 'src/app/model/OrderItem';
import { OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-table-order',
  templateUrl: './table-order.component.html',
  styleUrls: ['./table-order.component.less'],
})
export class TableOrderComponent implements OnInit {
  order?: Order;
  noOrder: boolean = false;

  @ViewChild(MatTable) table?: MatTable<OrderItem>;
  displayedColumns: string[] = ['id', 'name', 'amount', 'status'];

  dummyMenu = [
    { id: 1, name: 'Spaghetti carbonara', a: 0 },
    { id: 2, name: 'Chicken tikka masala', a: 0 },
    { id: 3, name: 'Mushroom risotto', a: 0 },
    { id: 4, name: 'Peach ice tea', a: 0 },
  ];
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { tableId: number },
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.orderService.getForTable(this.data.tableId).subscribe((d) => {
      this.order = d;
      this.noOrder = !d;
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

  addToOrder(orderItem: { id: number; name: string; amount: number }) {
    if (!orderItem.amount) return;
    if (!this.order) this.initOrder();
    let eoi = this.order?.orderItems.find((oi) => oi.itemId === orderItem.id);
    if (eoi) eoi.amount = orderItem.amount;
    else
      this.order?.orderItems.push({
        itemId: orderItem.id,
        amount: orderItem.amount,
        item: { name: orderItem.name },
      });
    this.table?.renderRows();
  }

  createOrder() {
    if (this.order) this.orderService.createOrder(this.order);
  }
}
