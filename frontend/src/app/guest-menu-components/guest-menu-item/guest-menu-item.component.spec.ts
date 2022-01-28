import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestMenuItemComponent } from './guest-menu-item.component';

describe('GuestMenuItemComponent', () => {
  let component: GuestMenuItemComponent;
  let fixture: ComponentFixture<GuestMenuItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuestMenuItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestMenuItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
