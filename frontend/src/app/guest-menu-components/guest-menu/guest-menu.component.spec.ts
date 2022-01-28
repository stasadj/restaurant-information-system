import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestMenuComponent } from './guest-menu.component';

describe('GuestMenuComponent', () => {
  let component: GuestMenuComponent;
  let fixture: ComponentFixture<GuestMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuestMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
