import { Component, OnInit } from '@angular/core';
import { TableService } from 'src/app/services/table/table.service';
import { RestaurantTable } from '../../model/RestaurantTable';
import { RoomOrganization } from '../../model/RoomOrganization';
import {
  AbstractControl,
  FormControl,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.less'],
})
export class CanvasComponent implements OnInit {
  rooms: RoomOrganization[] = [{ id: 'Room 0', tables: [] }];

  currentTable?: RestaurantTable;
  currentRoomIndex: number = 0;

  roomIdForm: FormControl;
  tableIdForm: FormControl;

  constructor(private tableService: TableService) {
    this.roomIdForm = new FormControl('Room', [
      Validators.pattern('^[0-9 A-Za-z]*$'),
      Validators.required,
      this.roomIdValidator(),
    ]);
    this.tableIdForm = new FormControl('0', [
      Validators.pattern('^[0-9]*$'),
      Validators.required,
      this.tableIdValidator(),
    ]);
  }

  ngOnInit(): void {
    this.tableService.getRooms().subscribe((data) => (this.rooms = data.rooms));

    this.roomIdForm.valueChanges.subscribe(
      (v: string) => (this.rooms[this.currentRoomIndex].id = v)
    );
    this.tableIdForm.valueChanges.subscribe((v: string) => {
      if (this.currentTable && this.tableIdForm.valid)
        this.currentTable.id = Number(v);
    });
  }

  onSave() {
    this.tableService.saveRooms(this.rooms);
  }

  onTableClick(id: number) {
    if (this.tableIdForm.invalid || this.currentTable?.id === id) return;
    if (this.currentTable) this.currentTable.status = '';

    this.currentTable = this.rooms[this.currentRoomIndex].tables.find(
      (x) => x.id === id
    );
    if (this.currentTable) {
      this.currentTable.status = 'selected';
      this.tableIdForm.setValue(id + '');
    }
  }

  onAddTable(copy: boolean) {
    if (this.currentTable) this.currentTable.status = '';
    let newTable =
      copy && this.currentTable
        ? {
            id: 0,
            rotateValue: this.currentTable.rotateValue,
            size: { ...this.currentTable.size },
            radius: this.currentTable.radius,
            position: {
              x: this.currentTable.position.x + 10,
              y: this.currentTable.position.y + 10,
            },
            status: 'selected',
          }
        : {
            id: 0,
            rotateValue: 0,
            size: { w: 100, h: 50 },
            radius: 20,
            position: { x: 0, y: 0 },
            status: 'selected',
          };
    newTable.id = Math.floor(Math.random() * 10000);
    this.rooms[this.currentRoomIndex].tables.push(newTable);
    this.currentTable = newTable;
    this.tableIdForm.setValue(newTable.id + '');
    this.tableIdForm.markAsTouched();
  }

  onDeleteTable() {
    let i = this.rooms[this.currentRoomIndex].tables.findIndex(
      (x) => x === this.currentTable
    );
    if (i === -1) {
      console.log('This table is in another room');
      return;
    }
    this.rooms[this.currentRoomIndex].tables.splice(i, 1);
    this.currentTable = undefined;
    this.tableIdForm.setValue('0');
  }

  onAddRoom() {
    this.rooms.push({
      id: '',
      tables: [],
    });
    this.currentRoomIndex = this.rooms.length - 1;
    this.roomIdForm.setValue('');
  }

  onDeleteRoom() {
    this.rooms.splice(this.currentRoomIndex, 1);
    if (this.rooms.length === 0) this.onAddRoom();
    this.currentRoomIndex = 0;
  }

  formatDegrees = (value: number) => value + 'º';
  formatPx = (value: number) => value + 'px';

  rotate90() {
    if (!this.currentTable) return;
    let h = this.currentTable.size.h;
    let w = this.currentTable.size.w;
    let pos = this.currentTable.position;
    this.currentTable.position = {
      x: pos.x + (w - h) / 2,
      y: pos.y - (w - h) / 2,
    };
    this.currentTable.size.h = this.currentTable.size.w;
    this.currentTable.size.w = h;
  }

  roomIdValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) return null;
      return this.rooms.findIndex((r) => r.id === value) !== -1
        ? { exists: true }
        : null;
    };
  }
  tableIdValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;
      const value = Number(control.value);
      for (let room of this.rooms)
        for (let table of room.tables)
          if (table.id === value && table !== this.currentTable)
            return { exists: true };
      return null;
    };
  }
}
