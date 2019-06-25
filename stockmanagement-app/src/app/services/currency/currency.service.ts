import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  readonly current_currency_storage = 'CURRENT_CURRENCY';
  public currentCurrency: BehaviorSubject<String>;

  constructor() {
    this.currentCurrency = new BehaviorSubject<String>(JSON.parse(localStorage.getItem(this.current_currency_storage)));
  }

  setCurrentCurrency(currency: String) {
    this.currentCurrency.next(currency);
    localStorage.setItem(this.current_currency_storage, JSON.stringify(currency));
  }
}
