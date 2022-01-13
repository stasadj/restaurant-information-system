import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CookPageComponent } from './cook-page.component';

describe('CookPageComponent', () => {
  let component: CookPageComponent;
  let fixture: ComponentFixture<CookPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CookPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CookPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
