import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from 'src/app/services/stock.service';
import {IStock} from '../../model/IStock';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatSortModule, MatSort} from "@angular/material";

@Component({
    selector: 'app-stock-list',
    templateUrl: './stock-list.component.html',
    styleUrls: ['./stock-list.component.scss']
})
export class StockListComponent implements OnInit {
    displayedColumns: string[] = ['name', 'market', 'price', 'change1d'];
    //stocks: IStock[] | undefined;
    dataSource: MatTableDataSource<IStock>;


    constructor(private stockService: StockService) {

    }

  @ViewChild(MatSort) sort: MatSort;

    ngOnInit() {
        this.getStocks();
    }

    getStocks() {
        this.stockService.getStocks().subscribe(
            data => {
              this.dataSource = new MatTableDataSource(data);
              this.dataSource.sort = this.sort;
            },
            error => console.log(error)
        );
    }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
