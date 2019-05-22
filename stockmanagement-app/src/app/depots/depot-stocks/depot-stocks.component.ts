import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {DepotService} from "../../services/depot/depot.service";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";
import {StockSellComponent} from "../../stock/stock-sell/stock-sell.component";
import {TransactionService} from "../../services/transaction/transaction.service";
import {HoldingService} from "../../services/holding/holding.service";

@Component({
  selector: 'app-depot-stocks',
  templateUrl: './depot-stocks.component.html',
  styleUrls: ['./depot-stocks.component.scss']
})
export class DepotStocksComponent implements OnInit {
  displayedColumns: string[] = ['stock.name', 'amount', 'totalPrice', 'sell'];
  dataSource: MatTableDataSource<IHolding>;
  currentDepot: IDepot;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private depotService: DepotService,
              private holdingService: HoldingService,
              private transactionService: TransactionService,
              private sellStockDialog: MatDialog) {
  }

  ngOnInit() {
    this.getHoldings();
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.currentDepot = depot;
    });
  }

  getHoldings() {
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.holdingService.getAllHoldings(depot.id).subscribe(data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      });
    });
  }

  openSellStockDialog(holding: IHolding): void {
    const dialogRef = this.sellStockDialog.open(StockSellComponent, {
      width: '300px',
      data: {holding: holding, amount: 1, price: holding.stock.price},
    });

    dialogRef.afterClosed().subscribe(data => {
      this.transactionService.sellStock(data.holding.stock, this.currentDepot, data.amount, data.price).subscribe(
        holding => {
          this.getHoldings()
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }


}
