import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepotStocksComponent } from './depot-stocks.component';

describe('DepotStocksComponent', () => {
  let component: DepotStocksComponent;
  let fixture: ComponentFixture<DepotStocksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepotStocksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepotStocksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
