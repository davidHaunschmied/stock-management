import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'currency-switch',
  templateUrl: './currency-switch.component.html',
  styleUrls: ['./currency-switch.component.scss']
})
export class CurrencySwitchComponent implements OnInit {
  currentCurrency: string;
  currencies: string[];
  filter = new FormControl();

  constructor() {
    this.currentCurrency = "EUR";
    this.currencies = ["EUR", "USD", "PLN"];
  }

  ngOnInit(): void {
    this.filter.setValue(this.currentCurrency);
  }

  refreshCurrentCurrency() {
    this.currentCurrency = this.filter.value;
  }
}
