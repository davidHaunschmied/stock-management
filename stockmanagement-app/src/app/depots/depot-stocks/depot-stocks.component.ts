import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {DepotService} from "../../services/depot/depot.service";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";

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

  constructor(private depotService: DepotService) {
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

  openSellStockDialog() {
    console.log("TODO");
  }
}
