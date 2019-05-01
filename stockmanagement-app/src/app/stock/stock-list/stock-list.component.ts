import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from 'src/app/services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog, MatPaginator, MatSort} from "@angular/material";
import {AlarmCreateDialogComponent} from "../../alert/alert-create-dialog/alarm-create-dialog.component";
import {AlarmService} from "../../services/alarm/alarm.service";

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.scss']
})
export class StockListComponent implements OnInit {
  displayedColumns: string[] = ['name', 'market', 'price', 'day_change', 'alert'];
  //stocks: IStock[] | undefined;
  dataSource: MatTableDataSource<IStock>;


  constructor(private stockService: StockService,
              private alarmService: AlarmService,
              private createAlarmDialog: MatDialog) {
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.getStocks();

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
