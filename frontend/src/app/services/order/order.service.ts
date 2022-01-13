import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from 'src/app/model/Order';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Subject } from 'rxjs';
import { OrderItem } from 'src/app/model/OrderItem';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private readonly path: string = '/api/order';
  private readonly wsPath: string = '/api/websocket';
  private stompClient: Stomp.Client;

  readonly orderSubject: Subject<Order> = new Subject();
  readonly orderItemsSubject: Subject<OrderItem[]> = new Subject();
  readonly cancelledItemsSubject: Subject<number[]> = new Subject();

  constructor(private http: HttpClient) {
    this.stompClient = Stomp.over(new SockJS(this.wsPath));
  }

  connect() {
    console.log('Initialize WebSocket connection');
    this.stompClient = Stomp.over(new SockJS(this.wsPath));
    this.stompClient.connect(
      {},
      () => {
        this.stompClient.subscribe('/topic/orders', (m) =>
          this.onOrderReceived(m)
        );
        this.stompClient.subscribe('/topic/order-items', (m) =>
          this.onOrderItemsReceived(m)
        );
        this.stompClient.subscribe('/topic/cancelled-items', (m) =>
          this.onCancelledItemsReceived(m)
        );
      },
      this.errorCallBack
    );
  }

  disconnect() {
    this.stompClient?.disconnect(() => console.log('Disconnected'));
  }

  private errorCallBack(error: any) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  private onOrderReceived(message: Stomp.Message) {
    this.orderSubject.next(JSON.parse(message.body));
  }
  private onOrderItemsReceived(message: Stomp.Message) {
    this.orderItemsSubject.next(JSON.parse(message.body));
  }
  private onCancelledItemsReceived(message: Stomp.Message) {
    this.cancelledItemsSubject.next(JSON.parse(message.body));
  }

  getAll(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.path}/all`);
  }

  getAllForWaiter(id: number): Observable<HttpResponse<Order[]>> {
    return this.http.get<HttpResponse<Order[]>>(`${this.path}/all/${id}`);
  }

  createOrder(order: Order) {
    this.http
      .post(`${this.path}/create`, order)
      .subscribe((d) => console.log('Saved'));
  }

  editOrder(order: Order) {
    this.http
      .put(`${this.path}/edit`, order)
      .subscribe((d) => console.log('Saved'));
  }

  finalizeOrder(id: number) {
    this.http
      .delete(`${this.path}/finalize/${id}`)
      .subscribe((d) => console.log('Finalized'));
  }
}