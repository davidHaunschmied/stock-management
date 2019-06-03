import {Component, OnInit} from '@angular/core';
import {StockService} from '../../services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {ActivatedRoute, Router} from '@angular/router';
import * as Highcharts from 'highcharts/highstock';
import {IHistoryPoint} from "../../model/IHistoryPoint";

@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.scss']
})
export class StockDetailsComponent implements OnInit {

  param: string;
  stock: IStock;
  stockHistory: IHistoryPoint [];
  Highcharts = Highcharts;
  chartOptions: Object;

  constructor(private stockService: StockService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    // https://stackoverflow.com/a/48446698
    this.route.params.subscribe(
      params => {
        const id = +params['id'];
        if (id) {
          this.getStockDetails(id);
          this.getStockHistory(id);
        }
      }
    );
  }

  getStockDetails(id: number) {
    this.stockService.getStockDetails(id).subscribe(data => {
        this.stock = data;
      }, error => {
        console.log(error);
      }
    );
  }

  onBack(): void {
    this.router.navigate(['/stocks']);
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
}
