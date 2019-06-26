import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'currency-switch',
  templateUrl: './currency-switch.component.html',
  styleUrls: ['./currency-switch.component.scss']
})
export class CurrencySwitchComponent implements OnInit {

  currencies: String[];
  filter = new FormControl();

  constructor() {
    this.currencies = ["EUR", "USD", "PLN"];
  }

  ngOnInit(): void {
    //this.filter.setValue(currency);
  }

  refreshCurrentCurrency() {
    let currency = this.filter.value;
  }
}
