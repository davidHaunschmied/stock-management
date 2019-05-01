import {Component, Input, OnInit} from '@angular/core';
import {StockService} from '../../services/stock/stock.service';
import {IStock} from '../../model/IStock';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.scss']
})
export class StockDetailsComponent implements OnInit {

//  @Input()
//  id: number;

  param: string;
  stock: IStock;

  constructor(private stockService: StockService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    const param = this.route.snapshot.paramMap.get('id');
    if (param) {
      const id = +param;
      this.getStockDetails(id);
    }
  }


  getStockDetails(id: number) {
    this.stockService.getStockDetails(id).subscribe(data => {
        this.stock = data;
      }, error => {
        console.log(error);
      }
    );
  }

  onBack(): void {
    this.router.navigate(['/stocks']);
  }

}
