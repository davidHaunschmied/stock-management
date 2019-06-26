import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DepotImportDialogComponent} from './depot-import-dialog.component';

describe('DepotImportDialogComponent', () => {
  let component: DepotImportDialogComponent;
  let fixture: ComponentFixture<DepotImportDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DepotImportDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepotImportDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
