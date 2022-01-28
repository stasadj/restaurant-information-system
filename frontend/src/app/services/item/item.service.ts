import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ItemValue } from 'src/app/model/ItemValue';
import { Item } from '../../model/Item';

@Injectable({
    providedIn: 'root',
})
export class ItemService {
    private readonly path = '/api/item';


    constructor(private http: HttpClient) { }

    getItems(): Observable<Item[]> {
        return this.http.get<Item[]>(this.path + "/all");
    }

    addToMenu(item: Item): Observable<Item> {
        return this.http.put<Item>(this.path + "/add-to-menu/" + item.id, null);
    }

    removeFromMenu(item: Item): Observable<Item> {
        return this.http.put<Item>(this.path + "/remove-from-menu/" + item.id, null);
    }

    delete(item: Item) {
        return this.http.delete(this.path + "/" + item.id);
    }

    edit(item: Item): Observable<Item> {
        return this.http.put<Item>(this.path + "/edit", item);
    }

    changeItemPrice(itemValue: ItemValue): Observable<ItemValue> {
        return this.http.post<ItemValue>(this.path + "/change-price", itemValue);
    }
  
    getMenuItems(): Observable<Item[]> {
      return this.http.get<Item[]>(this.path + '/in-menu');
    }

    searchMenuItems(categoryId: number, searchInput: string): Observable<Item[]> {
      return this.http.get<Item[]>(
        `${this.path}/search?categoryId=${categoryId}&searchInput=${searchInput}`
      );
    }



    create(item: Item, file: File): Observable<Item> {

        const formData = new FormData();
        if (file) {
            formData.append("file", file, file.name);
        }
        formData.append('item', new Blob([JSON.stringify(item)], {
            type: "application/json"
        }));

        // !!! do not set Content-type multipart/form-data manually
        // The browser will automatically set both the multipart/form-data AND the boundary necessary for proper backend parsing 
        // let headers = new HttpHeaders();
        // headers = headers.set('Content-Type', 'multipart/form-data');

        return this.http.post<Item>(this.path + "/createWithFile", formData);
    }


    getItemsByCategory(categoryId: number): Observable<Item[]> {
        return this.http.get<Item[]>(`${this.path}/category/${categoryId}`);
    }
}
