import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from 'src/app/services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatPaginator, MatSort} from "@angular/material";
import {AlertCreateDialogComponent} from "../../alert/alert-create-dialog/alert-create-dialog.component";
import {AlertService} from "../../services/alert/alert.service";
import {StockPurchaseComponent} from "../stock-purchase/stock-purchase.component";
import {TransactionService} from "../../services/transaction/transaction.service";
import {IDepot} from "../../model/IDepot";
import {DepotService} from "../../services/depot/depot.service";

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
              private alertService: AlertService,
              private createAlertDialog: MatDialog,
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
    this.stockService.getStocks().subscribe(
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


  openCreateAlertDialog(name: string, price: number): void {
    const dialogRef = this.createAlertDialog.open(AlertCreateDialogComponent, {
      width: '300px',
      data: {price: price, stock: name, value: price},
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log('The dialog was closed');
      this.alertService.createAlert(data.stock, data.value).subscribe(
        data => {
          console.log('Added alert of data: ' + data);
        }, error => {
          console.log('Error: ' + error);
        }
      );
    });
  }


  openBuyStockDialog(stock: any) {
    const dialogRef = this.purchaseStockDialog.open(StockPurchaseComponent, {
      width: '250px',
      data: {stock: stock, amount: 1, totalPrice: stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.transactionService.purchaseStock(data.holding.stock, this.currentDepot, data.amount, data.totalPrice).subscribe(
        holding => {
          this.getStocks()
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });

  }
}
