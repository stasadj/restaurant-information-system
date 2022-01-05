import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { TableService } from 'src/app/services/table/table.service';

import { MapComponent } from './map.component';
import { DebugElement } from '@angular/core';

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;
  let tableService: TableService;

  beforeEach(async () => {
    let tableServiceMock = {
      getRooms: jasmine.createSpy('getRooms').and.returnValue(
        of({
          rooms: [
            {
              id: 'Room 1',
              tables: [
                {
                  id: 1,
                  rotateValue: 0,
                  size: { w: 150, h: 50 },
                  radius: 10,
                  position: { x: 0, y: 0 },
                },
                {
                  id: 2,
                  rotateValue: 30,
                  size: { w: 50, h: 50 },
                  radius: 20,
                  position: { x: 100, y: 0 },
                },
              ],
            },
            {
              id: 'Room 2',
              tables: [
                {
                  id: 3,
                  rotateValue: 45,
                  size: { w: 120, h: 50 },
                  radius: 10,
                  position: { x: 50, y: 50 },
                },
              ],
            },
          ],
        })
      ),
    };

    await TestBed.configureTestingModule({
      declarations: [MapComponent],
      providers: [{ provide: TableService, useValue: tableServiceMock }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapComponent);
    tableService = TestBed.inject(TableService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load rooms & tables', () => {
    component.ngOnInit();

    expect(tableService.getRooms).toHaveBeenCalled();
    fixture.whenStable().then(() => {
      expect(component.rooms.length).toBe(2);
      fixture.detectChanges();

      let roomTabs = fixture.debugElement.queryAll(By.css('mat-tab'));
      expect(roomTabs.length).toBe(2); // 2 rooms
      let tables1 = roomTabs[0].queryAll(By.css('app-table'));
      expect(tables1.length).toBe(2); // 2 tables in first room
      let tables2 = roomTabs[1].queryAll(By.css('app-table'));
      expect(tables2.length).toBe(1); // 1 table in second room
    });
  });
});
