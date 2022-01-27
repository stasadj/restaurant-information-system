import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import axios from 'axios';
import { Observable } from 'rxjs';
import { ItemValue } from 'src/app/model/ItemValue';
import { Item } from '../../model/Item';

@Injectable({
    providedIn: 'root'
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

    async create(item: Item, file: File): Promise<Item> {

        const formData = new FormData();
        formData.append("file", file, file.name);
        formData.append('item', new Blob([JSON.stringify(item)], {
            type: "application/json"
        }));

        let response =  await axios.post(
            this.path + "/createWithFile",
            formData,
            {headers: { "Content-Type": "multipart/form-data" }
        })

        return response.data;





    }
}
