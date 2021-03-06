import {Component, OnInit} from '@angular/core';
import {DepotService} from "../../services/depot/depot.service";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";
import {IHistoryPoint} from "../../model/IHistoryPoint";
import {HoldingService} from "../../services/holding/holding.service";
import * as Highcharts from "highcharts/highstock";

@Component({
  selector: 'app-depot-overview',
  templateUrl: './depot-overview.component.html',
  styleUrls: ['./depot-overview.component.scss']
})
export class DepotOverviewComponent implements OnInit {

  holdings: IHolding[];
  absoluteChange: number;
  relativeChange: number;
  totalEarnings: number;
  depotHistory: IHistoryPoint[];
  Highcharts = Highcharts;
  chartOptions: Object;
  depot: IDepot;
  private file: File;
  newDepotName: string;
  currencies = ["USD", "PLN"];
  currencyEarnings: Map<string, number>;

  constructor(
    private depotService: DepotService,
    private holdingService: HoldingService
  ) {
  }

  ngOnInit() {
    this.currencyEarnings = new Map<string, number>();
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      if (depot) {
        this.depot = depot;
        this.initData();
        this.initChart();
        this.fetchCurrencyEarnings();
      }
    });
  }

  private initChart() {
    this.depotService.getHistory(this.depot.id).subscribe(data => {
      this.depotHistory = data;
      this.chartOptions = {
        series: [{
          name: this.depot.name,
          data: this.depotHistory.map(function (day) {
            return [day.dateMillis, day.price];
          }),
          tooltip: {
            valueDecimals: 2
          }
        }]
      };
    });
  }

  private initData() {
    this.holdingService.getAllHoldings(this.depot.id).subscribe(data => {
      this.holdings = data;
      this.calculateAbsoluteChange();
      this.calculateRelativeChange();
      this.calculateTotalEarnings();
    });
  }

  calculateAbsoluteChange() {
    let absolutChange = 0;
    this.holdings.forEach(holding => {
      absolutChange += holding.amount * holding.stock.price;
      absolutChange -= holding.totalPrice;
    });
    this.absoluteChange = absolutChange;
  }

  calculateRelativeChange() {
    let holdingValue = 0;
    this.holdings.forEach(holding => {
      holdingValue += holding.totalPrice;
    });
    this.relativeChange = this.absoluteChange / holdingValue * 100;
    if (!this.relativeChange) {
      this.relativeChange = 0;
    }
  }

  calculateTotalEarnings() {
    let totalEarnings = 0;
    this.holdings.forEach(holding => {
      holding.earnings.forEach(earning => {
        totalEarnings += earning.earnings;
      })
    });
    this.totalEarnings = totalEarnings;
  }

  getTotalDevelopment() {
    return this.totalEarnings + this.absoluteChange;
  }

  getExportLink() {
    return 'http://localhost:8080/api/depots/export/' + this.depot.id;
  }

  fetchCurrencyEarnings() {
    this.currencies.forEach(currency => {
      this.depotService.getCurrencyEarnings(this.depot.id, currency).subscribe(earnings => {

        this.currencyEarnings.set(currency, earnings);
      });
    });
    this.currencyEarnings.set('USD', 10);
  }

  getCurrencyEarnings(currency: string) {
    const earning = this.currencyEarnings.get(currency);
    return earning ? earning : 0;
  }
}
