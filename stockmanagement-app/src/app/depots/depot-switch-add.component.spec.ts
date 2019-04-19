import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotSwitchAddComponent } from './depot-switch-add.component';

describe('DepotSwitchAddComponent', () => {
  let component: DepotSwitchAddComponent;
  let fixture: ComponentFixture<DepotSwitchAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepotSwitchAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepotSwitchAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
