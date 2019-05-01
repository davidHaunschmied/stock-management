import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from 'src/app/services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatPaginator, MatSort} from "@angular/material";
import {AlarmService} from "../../services/alarm/alarm.service";
import {StockPurchaseComponent} from "../stock-purchase/stock-purchase.component";
import {TransactionService} from "../../services/transaction/transaction.service";
import {IDepot} from "../../model/IDepot";
import {DepotService} from "../../services/depot/depot.service";
import {AlarmCreateDialogComponent} from "../../alert/alert-create-dialog/alarm-create-dialog.component";

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.scss']
})
export class StockListComponent implements OnInit {
  displayedColumns: string[] = ['name', 'market', 'price', 'change1d', 'buy', 'alert'];
  //stocks: IStock[] | undefined;
  dataSource: MatTableDataSource<IStock>;
  private currentDepot: IDepot;


  constructor(private stockService: StockService,
              private alarmService: AlarmService,
              private createAlarmDialog: MatDialog,
              private purchaseStockDialog: MatDialog,
              private transactionService: TransactionService,
              private depotService: DepotService) {
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.getStocks();
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.currentDepot = depot;
    });

  }

  getStocks() {
    this.stockService.getAllStocks().subscribe(
      data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error => console.log(error)
    );
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  openCreateAlarmDialog(stock : IStock): void {
    const dialogRef = this.createAlarmDialog.open(AlarmCreateDialogComponent, {
      width: '300px',
      data: {stock: stock, alarmPrice: stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log('The dialog was closed');/*
      this.alarmService.createAlarm(data, data.alarmPrice).subscribe(
        data => {
          console.log('Added alarm of data: ' + data);
        }, error => {
          console.log('Error: ' + error);
        }
      );*/
    });
  }


  openBuyStockDialog(stock: any) {
    const dialogRef = this.purchaseStockDialog.open(StockPurchaseComponent, {
      width: '300px',
      data: {stock: stock, amount: 1, totalPrice: stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.transactionService.purchaseStock(data.stock, this.currentDepot, data.amount, data.totalPrice).subscribe(
        holding => {
          this.getStocks()
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });

  }
}
