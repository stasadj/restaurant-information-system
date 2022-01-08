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
  
    getItems(): Observable<HttpResponse<Item[]>> {
      return this.http.get<HttpResponse<Item[]>>(this.path);
    }
}
