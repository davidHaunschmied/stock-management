import {Component, OnInit} from '@angular/core';
import {StockService} from '../../services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {ActivatedRoute, Router} from '@angular/router';
import * as Highcharts from 'highcharts/highstock';
import {IHistoryPoint} from "../../model/IHistoryPoint";
import {AlarmCreateDialogComponent} from "../../alarm/alarm-create-dialog/alarm-create-dialog.component";
import {AlarmService} from "../../services/alarm/alarm.service";
import {MatDialog, MatTableDataSource} from "@angular/material";
import {StockPurchaseComponent} from "../stock-purchase/stock-purchase.component";
import {TransactionService} from "../../services/transaction/transaction.service";
import {IDepot} from "../../model/IDepot";
import {DepotService} from "../../services/depot/depot.service";
import {IHolding} from "../../model/IHolding";
import {StockSellComponent} from "../stock-sell/stock-sell.component";
import {HoldingService} from "../../services/holding/holding.service";

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
  currentDepot: IDepot;
  holding: IHolding;
  holdings: IHolding[];

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
          this.getStockHistory(id);
      //this.getDepotsOfHolding();
        }
        this.depotService.currentDepot.subscribe((depot: IDepot) => {
          this.currentDepot = depot;
        });

      }
    );
  }

  getStockDetails(id: number) {
    this.stockService.getStockDetails(id).subscribe(data => {
      this.holdingService.getAllHoldingsByStock(data.id).subscribe(data => {
        data = data.filter(holding => {
          console.log(holding.depot);
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

  private getStockHistory(id: number) {
    this.stockService.getStockHistory(id).subscribe(data => {
        this.stockHistory = data;
        this.chartOptions = {
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
      this.transactionService.purchaseStock(data.stock, this.currentDepot, data.amount, data.totalPrice).subscribe(
        holding => {
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
      this.transactionService.sellStock(data.holding.stock, this.currentDepot, data.amount, data.price).subscribe(
        holding => {
          this.getDepotsOfHolding();
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  getDepotsOfHolding() {
      this.holdingService.getAllHoldingsByStock(this.stock.id).subscribe(data => {
        data = data.filter(holding => {

          return holding.amount > 0
        });
        this.holdings = data;
        this.holdings.forEach(holding => {
          if(holding.depot.id === this.currentDepot.id)
            this.holding = holding;
          console.log('test');
        })
      });

  }

}
