import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBoardComponent } from './report-board.component';

describe('ReportBoardComponent', () => {
  let component: ReportBoardComponent;
  let fixture: ComponentFixture<ReportBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportBoardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
