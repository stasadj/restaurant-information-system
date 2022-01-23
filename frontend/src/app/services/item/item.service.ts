import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Item } from '../../model/Item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

    private readonly path = '/api/item';

    constructor(private http: HttpClient) {}
  
    getItems(): Observable<Item[]> {
      return this.http.get<Item[]>(this.path + "/all");
    }

    addToMenu(item : Item): Observable<Item> {
        return this.http.put<Item>(this.path + "/add-to-menu/" + item.id, null);
    }

    removeFromMenu(item : Item): Observable<Item> {
        return this.http.put<Item>(this.path + "/remove-from-menu/" + item.id, null);
    }

    delete(item : Item) {
        return this.http.delete(this.path + "/" + item.id);
    }
}
