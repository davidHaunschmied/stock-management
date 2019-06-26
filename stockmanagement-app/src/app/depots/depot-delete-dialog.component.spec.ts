import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotDeleteDialogComponent } from './depot-delete-dialog.component';

describe('DepotDeleteDialogComponent', () => {
  let component: DepotDeleteDialogComponent;
  let fixture: ComponentFixture<DepotDeleteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepotDeleteDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepotDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
