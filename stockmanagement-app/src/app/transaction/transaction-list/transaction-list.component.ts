import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from 'src/app/services/stock/stock.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatPaginator, MatSort} from "@angular/material";
import {AlarmService} from "../../services/alarm/alarm.service";
import {TransactionService} from "../../services/transaction/transaction.service";
import {IDepot} from "../../model/IDepot";
import {DepotService} from "../../services/depot/depot.service";
import {ITransaction} from "../../model/ITransaction";

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit {
  displayedColumns: string[] = ['stock', 'symbol', 'market', 'amount', 'price', 'date', 'type'];
  dataSource: MatTableDataSource<ITransaction>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private stockService: StockService,
              private alarmService: AlarmService,
              private createAlarmDialog: MatDialog,
              private purchaseStockDialog: MatDialog,
              private transactionService: TransactionService,
              private depotService: DepotService) {
  }

  ngOnInit() {
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.getTransactions(depot.id);
    });
  }

  getTransactions(depotId: number) {
    this.transactionService.getAllTransactionsByDepot(depotId).subscribe(
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
}
