import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { TableService } from 'src/app/services/table.service';
import { RoomOrganization } from '../RoomOrganization';

import { CanvasComponent } from './canvas.component';

describe('CanvasComponent', () => {
  let component: CanvasComponent;
  let fixture: ComponentFixture<CanvasComponent>;
  let tableService: TableService;
  let data: { rooms: RoomOrganization[] };

  beforeEach(async () => {
    data = {
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
    };
    let tableServiceMock = {
      getRooms: jasmine.createSpy('getRooms').and.returnValue(of(data)),
      saveRooms: jasmine.createSpy('saveRooms'),
    };

    await TestBed.configureTestingModule({
      declarations: [CanvasComponent],
      providers: [{ provide: TableService, useValue: tableServiceMock }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasComponent);
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

  it('should save rooms', () => {
    let saveBtn = fixture.debugElement.query(By.css('#saveBtn'));
    saveBtn.triggerEventHandler('click', null);
    expect(tableService.saveRooms).toHaveBeenCalled();
  });

  it('should select table', () => {
    component.rooms = data.rooms;
    expect(component.currentTable).toBeFalsy();
    expect(fixture.debugElement.query(By.css('#tableSettings'))).toBeFalsy();
    expect(component.currentRoomIndex).toBe(0);

    component.onTableClick(1);

    expect(component.currentTable).toBeTruthy();
    expect(component.currentTable?.status).toBe('selected');
    expect(component.tableIdForm.value).toBe('1');

    fixture.detectChanges();

    expect(fixture.debugElement.query(By.css('#tableSettings'))).toBeTruthy();
  });

  it('should add new table', () => {
    component.rooms = data.rooms;
    component.currentRoomIndex = 1;
    expect(component.currentTable).toBeFalsy();

    component.onAddTable(false);

    expect(component.rooms[1].tables.length).toBe(2);
    expect(component.rooms[1].tables[1] === component.currentTable).toBeTrue();
    expect(component.currentTable?.position).toEqual({ x: 0, y: 0 });
    expect(component.currentTable?.rotateValue).toBe(0);
    expect(component.currentTable?.size).toEqual({ w: 100, h: 50 });
    expect(component.currentTable?.radius).toBe(20);
    expect(component.currentTable?.status).toBe('selected');
  });

  it('should add duplicate table', () => {
    component.rooms = data.rooms;
    component.currentRoomIndex = 1;
    let table = component.rooms[1].tables[0];
    component.currentTable = table;
    expect(component.currentTable).toBeTruthy();

    component.onAddTable(true);

    expect(component.rooms[1].tables.length).toBe(2);
    expect(component.rooms[1].tables[1] === component.currentTable).toBeTrue();
    expect(component.currentTable?.position).toEqual({
      x: table.position.x + 10,
      y: table.position.y + 10,
    });
    expect(component.currentTable?.rotateValue).toBe(table.rotateValue);
    expect(component.currentTable?.size).toEqual(table.size);
    expect(component.currentTable?.radius).toBe(table.radius);
    expect(component.currentTable?.status).toBe('selected');
  });

  it('should delete table in current room', () => {
    component.rooms = data.rooms;
    component.currentRoomIndex = 0;
    component.currentTable = component.rooms[0].tables[0];

    component.onDeleteTable();

    expect(component.rooms[0].tables.length).toBe(1);
    expect(component.rooms[0].tables[0].id).toBe(2);
    expect(component.currentTable).toBeFalsy();
  });

  it('should not delete table room in another room', () => {
    component.rooms = data.rooms;
    component.currentRoomIndex = 0;
    component.currentTable = component.rooms[1].tables[0];

    component.onDeleteTable();

    expect(component.rooms[0].tables.length).toBe(2);
    expect(component.rooms[1].tables.length).toBe(1);
    expect(component.currentTable).toBeTruthy();
  });

  it('should add room', () => {
    component.rooms = [{ id: 'Room1', tables: [] }];
    component.currentRoomIndex = 0;

    component.onAddRoom();

    expect(component.rooms.length).toBe(2);
    expect(component.rooms[1].id).toBe('');
    expect(component.currentRoomIndex).toBe(1);
  });

  it('should delete room', () => {
    component.rooms = [
      { id: 'Room1', tables: [] },
      { id: 'Room2', tables: [] },
    ];
    component.currentRoomIndex = 0;

    component.onDeleteRoom();

    expect(component.rooms.length).toBe(1);
    expect(component.rooms[0].id).toBe('Room2');
    expect(component.currentRoomIndex).toBe(0);
  });

  it('should delete last room and add empty', () => {
    component.rooms = [{ id: 'Room1', tables: [] }];
    component.currentRoomIndex = 0;

    component.onDeleteRoom();

    expect(component.rooms.length).toBe(1);
    expect(component.rooms[0].id).toBe('');
    expect(component.currentRoomIndex).toBe(0);
  });

  it('should rotate table by 90deg', () => {
    let table = data.rooms[0].tables[0];
    component.currentTable = table;
    let oldW = table.size.w;
    let oldH = table.size.h;
    let oldRotateValue = table.rotateValue;

    component.rotate90();

    expect(component.currentTable.size).toEqual({ h: oldW, w: oldH });
    expect(component.currentTable.rotateValue).toBe(oldRotateValue);
  });

  it('should validate room id', () => {
    component.rooms = [
      { id: '', tables: [] },
      { id: 'Old room', tables: [] },
    ];
    component.currentRoomIndex = 0;

    component.roomIdForm.setValue('');
    expect(component.roomIdForm.valid).toBeFalse();

    component.roomIdForm.setValue('Room 1');
    expect(component.roomIdForm.valid).toBeTrue();

    component.roomIdForm.setValue('Room.1');
    expect(component.roomIdForm.valid).toBeFalse();

    component.roomIdForm.setValue('Old room');
    expect(component.roomIdForm.valid).toBeFalse();
  });

  it('should validate table id', () => {
    component.rooms = data.rooms;
    component.currentTable = component.rooms[0].tables[0];

    component.tableIdForm.setValue('');
    expect(component.tableIdForm.valid).toBeFalse();

    component.tableIdForm.setValue('10');
    expect(component.tableIdForm.valid).toBeTrue();

    component.tableIdForm.setValue('1a');
    expect(component.tableIdForm.valid).toBeFalse();

    component.tableIdForm.setValue('3');
    expect(component.tableIdForm.valid).toBeFalse();
  });
});
