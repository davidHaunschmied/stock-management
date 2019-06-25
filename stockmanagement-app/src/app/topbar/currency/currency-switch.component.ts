import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {CurrencyService} from "../../services/currency/currency.service";

@Component({
  selector: 'currency-switch',
  templateUrl: './currency-switch.component.html',
  styleUrls: ['./currency-switch.component.scss']
})
export class CurrencySwitchComponent implements OnInit {

  currencies: String[];
  showAlarms: boolean;
  filter = new FormControl();

  constructor(private currencyService: CurrencyService) {
    this.currencies = ["EUR", "USD", "PLN"];
  }

  ngOnInit(): void {
    this.currencyService.currentCurrency.subscribe(currency => {
      this.filter.setValue(currency);
    });
  }

  refreshCurrentCurrency() {
    this.currencyService.setCurrentCurrency(this.filter.value);
  }
}
