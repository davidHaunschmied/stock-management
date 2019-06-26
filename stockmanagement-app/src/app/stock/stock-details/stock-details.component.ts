import {Component, OnInit} from '@angular/core';
import {StockService} from '../../services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {ActivatedRoute, Router} from '@angular/router';
import * as Highcharts from 'highcharts/highstock';
import {IHistoryPoint} from "../../model/IHistoryPoint";
import {AlarmCreateDialogComponent} from "../../alarm/alarm-create-dialog/alarm-create-dialog.component";
import {AlarmService} from "../../services/alarm/alarm.service";
import {MatDialog} from "@angular/material";
import {StockPurchaseComponent} from "../stock-purchase/stock-purchase.component";
import {TransactionService} from "../../services/transaction/transaction.service";
import {DepotService} from "../../services/depot/depot.service";
import {IHolding} from "../../model/IHolding";
import {StockSellComponent} from "../stock-sell/stock-sell.component";
import {HoldingService} from "../../services/holding/holding.service";
import {IAlarm} from "../../model/IAlarm";

@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.scss']
})
export class StockDetailsComponent implements OnInit {
  stock: IStock;
  stockHistory: IHistoryPoint [];
  Highcharts = Highcharts;
  chartOptions: Object;
  holding: IHolding;
  holdings: IHolding[];
  private alarms: IAlarm [];

  constructor(private stockService: StockService,
              private route: ActivatedRoute,
              private router: Router,
              private alarmService: AlarmService,
              private purchaseStockDialog: MatDialog,
              private transactionService: TransactionService,
              private createAlarmDialog: MatDialog,
              private depotService: DepotService,
              private holdingService: HoldingService,
              private sellStockDialog: MatDialog) {
  }

  ngOnInit() {
    // https://stackoverflow.com/a/48446698
    this.route.params.subscribe(
      params => {
        const id = +params['id'];
        if (id) {
          this.getStockDetails(id);
          this.renderChart(id);
        }
        this.depotService.currentDepot.subscribe(() => {
          this.getDepotsOfHolding(id);
        });
      }
    );
  }

  getStockDetails(id: number) {
    this.stockService.getStockDetails(id).subscribe(data => {
      this.holdingService.getAllHoldingsByStock(data.id).subscribe(data => {
        data = data.filter(holding => {
          return holding.amount > 0
        });
        this.holdings = data;
      });
      this.stock = data;
      }, error => {
        console.log(error);
      }
    );
  }

  private renderChart(id: number) {
    this.stockService.getStockHistory(id).subscribe(data => {
      this.alarmService.getAllAlarmsByStockId(id).subscribe(alarms => {
        this.alarms = alarms;
        this.stockHistory = data;
        this.chartOptions = {
          xAxis: {
            title: {
              text: 'Zeit'
            }
          },
          yAxis: {
            title: {
              text: 'Aktueller Wert'
            },
            plotLines: this.getPlotLines()
          },
          series: [{
            name: this.stock.symbol,
            data: this.stockHistory.map(function (day) {
              return [day.dateMillis, day.price];
            }),
            tooltip: {
              valueDecimals: 2
            }
          }]
        };
      }, error => {
        console.log(error);
      }
    );
    });
  }

  openCreateAlarmDialog(): void {
    const dialogRef = this.createAlarmDialog.open(AlarmCreateDialogComponent, {
      width: '300px',
      data: {stock: this.stock, alarmPrice: this.stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == null)
        return;
      this.alarmService.createAlarm(data.stock, data.alarmPrice).subscribe(
        alarm => {
          console.log('Added alarm of data: ' + JSON.stringify(alarm));
          this.renderChart(this.stock.id);
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  openBuyStockDialog() {
    const dialogRef = this.purchaseStockDialog.open(StockPurchaseComponent, {
      width: '300px',
      data: {stock: this.stock, amount: 1, totalPrice: this.stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == null)
        return;
      this.transactionService.purchaseStock(data.stock, this.depotService.currentDepot.getValue(), data.amount, data.totalPrice).subscribe(
        holding => {
          this.holding = holding;
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  openSellStockDialog(): void {
    const dialogRef = this.sellStockDialog.open(StockSellComponent, {
      width: '300px',
      data: {holding: this.holding, amount: 1, price: this.holding.stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == null)
        return;
      this.transactionService.sellStock(data.holding.stock, this.depotService.currentDepot.getValue(), data.amount, data.price).subscribe(
        holding => {
          this.holding = holding;
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  getDepotsOfHolding(id: number) {
    this.holdingService.getAllHoldings(this.depotService.currentDepot.getValue().id).subscribe(data => {
        data = data.filter(holding => {
          return holding.amount > 0
        });
        this.holdings = data;
        this.holdings.forEach(holding => {
          if (holding.stock.id === id)
            this.holding = holding;
        })
      });
  }


  hasHolding() {
    return this.holding && this.holding.amount > 0;
  }

  private getPlotLines() {
    const plotLines = [];
    const alarmUnder = this.alarms.find(alarm => {
      return alarm.alarmType == 'UNDER'
    });
    const alarmOver = this.alarms.find(alarm => {
      return alarm.alarmType == 'OVER'
    });
    if (alarmUnder) {
      plotLines.push({
        value: alarmUnder.price,
        color: 'red',
        dashStyle: 'longdash',
        width: 1,
        label: {
          text: 'Alarm ' + alarmUnder.price + ' ' + this.stock.currency
        }
      });
    }
    if (alarmOver) {
      plotLines.push({
        value: alarmOver.price,
        color: 'green',
        dashStyle: 'longdash',
        width: 1,
        label: {
          text: 'Alarm ' + alarmOver.price + ' ' + this.stock.currency
        }
      });
    }
    return plotLines;
  }

}
