import { Injectable } from '@angular/core';
import { RestaurantTable } from '../restaurant-map/RestaurantTable';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  constructor() {}

  getRooms(): { id: string; tables: RestaurantTable[] }[] {
    // TODO: api call
    return [
      {
        id: 'Room 1',
        tables: [
          {
            id: '1',
            rotateValue: 0,
            size: { w: 150, h: 50 },
            radius: 0,
            position: { x: 0, y: 0 },
            status: 'a',
          },
          {
            id: '2',
            rotateValue: 50,
            size: { w: 50, h: 50 },
            radius: 0,
            position: { x: 100, y: 0 },
            status: 'a',
          },
          {
            id: '3',
            rotateValue: 100,
            size: { w: 120, h: 50 },
            radius: 0,
            position: { x: 50, y: 50 },
            status: 'b',
          },
        ],
      },
      {
        id: 'Room 2',
        tables: [
          {
            id: '4',
            rotateValue: 0,
            size: { w: 150, h: 50 },
            radius: 0,
            position: { x: 60, y: 0 },
            status: 'a',
          },
          {
            id: '5',
            rotateValue: 50,
            size: { w: 50, h: 50 },
            radius: 0,
            position: { x: 100, y: 70 },
            status: 'a',
          },
        ],
      },
    ];
  }

  saveRooms(rooms: { id: string; tables: RestaurantTable[] }[]) {
    console.log(rooms);
    // TODO: post to backend
  }
}
