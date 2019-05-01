import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmCreateDialogComponent } from './alarm-create-dialog.component';

describe('AlarmCreateDialogComponent', () => {
  let component: AlarmCreateDialogComponent;
  let fixture: ComponentFixture<AlarmCreateDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlarmCreateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmCreateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
