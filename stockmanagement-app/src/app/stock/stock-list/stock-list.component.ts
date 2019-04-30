import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from 'src/app/services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort, MatDialog, MatPaginator} from "@angular/material";
import {AlarmCreateDialogComponent} from "../../alert/alert-create-dialog/alarm-create-dialog.component";
import {AlarmService} from "../../services/alarm/alarm.service";

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.scss']
})
export class StockListComponent implements OnInit {
  displayedColumns: string[] = ['name', 'market', 'price', 'change1d', 'alert'];
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


  openCreateAlertDialog(name: string, price: number): void {
    const dialogRef = this.createAlarmDialog.open(AlarmCreateDialogComponent, {
      width: '300px',
      data: {price: price, name: name, value: price},
    });

    dialogRef.afterClosed().subscribe(data => {
      console.log('The dialog was closed');
      this.alarmService.createAlert(data.name, data.value).subscribe(
        data => {
          console.log('Added alarm of data: ' + data);
        }, error => {
          console.log('Error: ' + error);
        }
      );
    });
  }


}
