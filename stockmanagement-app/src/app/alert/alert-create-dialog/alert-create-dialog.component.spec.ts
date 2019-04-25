import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertCreateDialogComponent } from './alert-create-dialog.component';

describe('AlertCreateDialogComponent', () => {
  let component: AlertCreateDialogComponent;
  let fixture: ComponentFixture<AlertCreateDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertCreateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertCreateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
