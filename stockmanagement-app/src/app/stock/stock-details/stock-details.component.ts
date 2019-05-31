import {Component, OnInit} from '@angular/core';
import {StockService} from '../../services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {ActivatedRoute, Router} from '@angular/router';
import * as Highcharts from 'highcharts/highstock';
import {IHistoryPoint} from "../../model/IHistoryPoint";
import {AlarmCreateDialogComponent} from "../../alarm/alarm-create-dialog/alarm-create-dialog.component";
import {AlarmService} from "../../services/alarm/alarm.service";
import {MatDialog} from "@angular/material";

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

  constructor(private stockService: StockService,
              private route: ActivatedRoute,
              private router: Router,
              private alarmService: AlarmService,
              private createAlarmDialog: MatDialog) {
  }

  ngOnInit() {
    const param = this.route.snapshot.paramMap.get('id');
    if (param) {
      const id = +param;
      this.getStockDetails(id);
      this.getStockHistory(id);
    }
  }


  getStockDetails(id: number) {
    this.stockService.getStockDetails(id).subscribe(data => {
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

}
