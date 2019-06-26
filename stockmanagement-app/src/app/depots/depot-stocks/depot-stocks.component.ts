import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {DepotService} from "../../services/depot/depot.service";
import {IDepot} from "../../model/IDepot";
import {StockSellComponent} from "../../stock/stock-sell/stock-sell.component";
import {TransactionService} from "../../services/transaction/transaction.service";
import {HoldingService} from "../../services/holding/holding.service";
import {IHoldingDetail} from "../../model/IHoldingDetail";
import {IHolding} from "../../model/IHolding";

@Component({
  selector: 'app-depot-stocks',
  templateUrl: './depot-stocks.component.html',
  styleUrls: ['./depot-stocks.component.scss']
})
export class DepotStocksComponent implements OnInit {
  displayedColumns: string[] = ['stock.name', 'stock.symbol', 'stock.stockExchange', 'amount', 'price', 'currentPrice', 'totalPrice', 'currentTotalPrice', 'absoluteChange', 'relativeChange', 'sell'];
  holdings: IHoldingDetail[];
  dataSource: MatTableDataSource<IHoldingDetail>;
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
      if (depot){
        this.currentDepot = depot;
      }
    });
  }

  getHoldings() {
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.holdingService.getAllHoldings(depot.id).subscribe(data => {
        data = data.filter(holding => {
          return holding.amount > 0
        });
        this.holdings = this.initHoldingDetail(data);
        this.dataSource = new MatTableDataSource(this.holdings);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      });
    });
  }

  openSellStockDialog(holding: IHolding): void {
    const dialogRef = this.sellStockDialog.open(StockSellComponent, {
      width: '300px',
      data: {holding: holding, amount: 1},
    });

    dialogRef.afterClosed().subscribe(data => {
      if (data == null)
        return;
      this.transactionService.sellStock(data.holding.stock, this.currentDepot, data.amount).subscribe(
        holding => {
          this.getHoldings()
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  private initHoldingDetail(holdings: IHolding[]): IHoldingDetail[] {
    let details: IHoldingDetail[] = [];

    holdings.forEach(holding => {
      let detail = {
        depot: holding.depot,
        amount: holding.amount,
        id: holding.id,
        stock: holding.stock,
        totalPrice: holding.totalPrice,
        earnings: holding.earnings,
        absoluteChange: holding.amount * holding.stock.price - holding.totalPrice,
        relativeChange: (holding.amount * holding.stock.price - holding.totalPrice) / holding.totalPrice * 100,
        currentTotalPrice: holding.stock.price * holding.amount
      };
      details.push(detail)
    });
    return details;
  }
}
