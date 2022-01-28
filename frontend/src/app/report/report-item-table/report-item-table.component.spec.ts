import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportItemTableComponent } from './report-item-table.component';

describe('ReportItemTableComponent', () => {
  let component: ReportItemTableComponent;
  let fixture: ComponentFixture<ReportItemTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportItemTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportItemTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
