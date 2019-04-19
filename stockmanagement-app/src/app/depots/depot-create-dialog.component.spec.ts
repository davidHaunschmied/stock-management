import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DepotCreateDialogComponent} from './depot-create-dialog.component';
import {TestcoreModule} from "../core/testcore.module";

describe('DepotCreateDialogComponent', () => {
  let component: DepotCreateDialogComponent;
  let fixture: ComponentFixture<DepotCreateDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        TestcoreModule
      ],
      declarations: [DepotCreateDialogComponent]
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
