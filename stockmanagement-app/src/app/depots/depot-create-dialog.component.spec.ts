import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotCreateDialogComponent } from './depot-create-dialog.component';

describe('DepotCreateDialogComponent', () => {
  let component: DepotCreateDialogComponent;
  let fixture: ComponentFixture<DepotCreateDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepotCreateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepotCreateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
