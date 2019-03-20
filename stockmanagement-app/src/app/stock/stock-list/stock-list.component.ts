import {Component, OnInit} from '@angular/core';
import {StockService} from 'src/app/services/stock.service';
import {IStock} from '../../model/IStock';
import {MatTableModule} from '@angular/material/table';

@Component({
    selector: 'app-stock-list',
    templateUrl: './stock-list.component.html',
    styleUrls: ['./stock-list.component.sass']
})
export class StockListComponent implements OnInit {
    displayedColumns: string[] = ['name', 'market', 'price', 'change1d'];
    stocks: IStock[] | undefined;

    constructor(private stockService: StockService) {

    }

    ngOnInit() {
        this.getStocks();
    }

    getStocks() {
        this.stockService.getStocks().subscribe(
            data => this.stocks = data,
            error => console.log(error)
        );
    }

}
