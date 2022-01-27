import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaiterMenuComponent } from './waiter-menu.component';

describe('WaiterMenuComponent', () => {
  let component: WaiterMenuComponent;
  let fixture: ComponentFixture<WaiterMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaiterMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WaiterMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
