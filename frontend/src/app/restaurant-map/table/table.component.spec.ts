import { CdkDrag } from '@angular/cdk/drag-drop';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { TableComponent } from './table.component';

describe('TableComponent', () => {
  let component: TableComponent;
  let fixture: ComponentFixture<TableComponent>;
  let tableDiv: HTMLDivElement;
  let cdkDrag: CdkDrag;

  beforeEach(async () => {
    let dragMock = {
      getFreeDragPosition: jasmine
        .createSpy('getFreeDragPosition')
        .and.returnValue({ x: 60, y: 70 }),
    };

    await TestBed.configureTestingModule({
      declarations: [TableComponent],
      providers: [{ provide: CdkDrag, useValue: dragMock }],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableComponent);
    component = fixture.componentInstance;
    cdkDrag = TestBed.inject(CdkDrag);
    fixture.detectChanges();
    tableDiv = fixture.debugElement.query(
      By.css('.table-in-restaurant')
    ).nativeElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have style', () => {
    component.table = {
      id: 1,
      rotateValue: 45,
      size: { w: 100, h: 50 },
      radius: 10,
      position: { x: 60, y: 70 },
      status: 'selected',
    };
    component.disableDrag = false;
    fixture.detectChanges();

    expect(tableDiv.style.transform).toBe('rotate(45deg)');
    expect(tableDiv.style.width).toBe('100px');
    expect(tableDiv.style.height).toBe('50px');
    expect(tableDiv.style.borderRadius).toBe('10px');
    expect(tableDiv.style.border).toBe('5px dashed green');

    let label = tableDiv.querySelector('b');
    expect(label?.style.transform).toBe('rotate(-45deg)');
    expect(label?.innerText).toBe('1');

    expect(tableDiv.parentElement?.style.width).toBe('100px');
    expect(tableDiv.parentElement?.style.height).toBe('50px');
    expect(tableDiv.parentElement?.style.cursor).toBe('move');
  });

  it('should change position', () => {
    expect(component.table.position.x).not.toBe(60);
    expect(component.table.position.y).not.toBe(70);

    component.dragEnd({ source: cdkDrag });

    expect(component.table.position.x).toBe(60);
    expect(component.table.position.y).toBe(70);
  });
});
