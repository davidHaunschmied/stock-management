import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {DepotService} from "../../services/depot/depot.service";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";
import {StockSellComponent} from "../../stock/stock-sell/stock-sell.component";
import {TransactionService} from "../../services/transaction/transaction.service";

@Component({
  selector: 'app-depot-stocks',
  templateUrl: './depot-stocks.component.html',
  styleUrls: ['./depot-stocks.component.scss']
})
export class DepotStocksComponent implements OnInit {
  displayedColumns: string[] = ['stock.name', 'amount', 'totalPrice', 'alert'];
  dataSource: MatTableDataSource<IHolding>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private depotService: DepotService,
              private transactionService: TransactionService,
              private sellStockDialog: MatDialog) {
  }

  ngOnInit() {
    this.getHoldings();
  }

  getHoldings() {
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.depotService.getAllHoldings(depot.id).subscribe(data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      });
    });
  }

  openSellStockDialog(holding: IHolding): void {
    const dialogRef = this.sellStockDialog.open(StockSellComponent, {
      width: '250px',
      data: {holding: holding},
    });

    dialogRef.afterClosed().subscribe(t => {
      this.transactionService.createTransaction(t);
    });
  }


}
