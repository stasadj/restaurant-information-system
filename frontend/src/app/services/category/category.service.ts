import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from 'src/app/model/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

    private readonly path = '/api/categories';

    constructor(private http: HttpClient) {}
  
    getItems(): Observable<Category[]> {
      return this.http.get<Category[]>(this.path);
    }
}
