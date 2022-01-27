import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, ObservedValueOf } from 'rxjs';
import { Item } from '../../model/Item';
import { ItemValue } from 'src/app/model/ItemValue';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private readonly path = '/api/item';

  constructor(private http: HttpClient) {}

  getItems(): Observable<Item[]> {
    return this.http.get<Item[]>(this.path + '/all');
  }

  getMenuItems(): Observable<Item[]> {
    return this.http.get<Item[]>(this.path + '/in-menu');
  }

  addToMenu(item: Item): Observable<Item> {
    return this.http.put<Item>(this.path + '/add-to-menu/' + item.id, null);
  }

  removeFromMenu(item: Item): Observable<Item> {
    return this.http.put<Item>(
      this.path + '/remove-from-menu/' + item.id,
      null
    );
  }

  delete(item: Item) {
    return this.http.delete(this.path + '/' + item.id);
  }

  edit(item: Item): Observable<Item> {
    return this.http.put<Item>(this.path + '/edit', item);
  }

  changeItemPrice(itemValue: ItemValue): Observable<ItemValue> {
    return this.http.post<ItemValue>(this.path + '/change-price', itemValue);
  }

  create(item: Item): Observable<Item> {
    return this.http.post<Item>(this.path + '/create', item);
  }
}
