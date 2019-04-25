import {Component, Input, OnInit} from '@angular/core';
import {StockService} from '../../services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.scss']
})
export class StockDetailsComponent implements OnInit {

  @Input()
  name: string;

  param: string;
  stock: IStock;

  constructor(private stockService: StockService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.getStockDetails(name);
  }


  getStockDetails(name: string) {
    this.stockService.getStockDetails(name).subscribe(data => {
        this.stock = data;
      }, error => {
        console.log(error);
      }
    );
  }

}
