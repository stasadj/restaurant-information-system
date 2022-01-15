import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderItem } from 'src/app/model/OrderItem';

@Injectable({
  providedIn: 'root',
})
export class OrderItemService {
  private readonly path: string = '/api/order-items';

  constructor(private http: HttpClient) {}

  acceptOrderItems(ids: number[]) {
    this.http
      .put<HttpResponse<{ message: string; data: OrderItem[] }>>(
        `${this.path}/accept`,
        { ids }
      )
      .subscribe((d) => console.log(d));
  }

  declineOrderItems(ids: number[]) {
    this.http
      .put<HttpResponse<{ message: string; data: OrderItem[] }>>(
        `${this.path}/decline`,
        { ids }
      )
      .subscribe((d) => console.log(d));
  }

  markOrderItemsAsPrepared(ids: number[]) {
    this.http
      .put<HttpResponse<{ message: string; data: OrderItem[] }>>(
        `${this.path}/mark-prepared`,
        { ids }
      )
      .subscribe((d) => console.log(d));
  }

  cancelOrderItem(
    ids: number[]
  ): Observable<{ data: number[]; message: string }> {
    return this.http.put<{ message: string; data: number[] }>(
      `${this.path}/cancel`,
      { ids }
    );
  }
}
