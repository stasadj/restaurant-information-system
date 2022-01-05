import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoomOrganization } from '../../model/RoomOrganization';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  private readonly path = '/api/table';

  constructor(private http: HttpClient) {}

  getRooms(): Observable<{ rooms: RoomOrganization[] }> {
    return this.http.get<{ rooms: RoomOrganization[] }>(this.path);
  }

  saveRooms(rooms: RoomOrganization[]) {
    console.log(rooms);
    this.http.post(this.path, { rooms }).subscribe((d) => console.log('Saved'));
  }
}
