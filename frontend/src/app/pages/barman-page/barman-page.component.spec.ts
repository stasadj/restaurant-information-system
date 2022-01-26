import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarmanPageComponent } from './barman-page.component';

describe('BarmanPageComponent', () => {
  let component: BarmanPageComponent;
  let fixture: ComponentFixture<BarmanPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BarmanPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BarmanPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
