import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from 'src/app/model/Tag';

@Injectable({
    providedIn: 'root'
})
export class TagService {

    private readonly path = '/api/tags';

    constructor(private http: HttpClient) { }

    getTags(): Observable<Tag[]> {
        return this.http.get<Tag[]>(this.path);
    }

    create(tag: Tag): Observable<Tag> {
        return this.http.post<Tag>(this.path, tag);
    }

    delete(tag: Tag): void {
        this.http.delete<Tag>(this.path + "/" + tag.id);
    }
}
