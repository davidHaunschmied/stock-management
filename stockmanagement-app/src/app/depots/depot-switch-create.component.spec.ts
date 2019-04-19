import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotSwitchCreateComponent } from './depot-switch-create.component';

describe('DepotSwitchCreateComponent', () => {
  let component: DepotSwitchCreateComponent;
  let fixture: ComponentFixture<DepotSwitchCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepotSwitchCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepotSwitchCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
