import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffBoardComponent } from './staff-board.component';

describe('StaffBoardComponent', () => {
  let component: StaffBoardComponent;
  let fixture: ComponentFixture<StaffBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StaffBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
