import {Component, OnInit} from '@angular/core';
import {DepotService} from "../../services/depot/depot.service";
import {IDepot} from "../../model/IDepot";
import {IHolding} from "../../model/IHolding";

@Component({
  selector: 'app-depot-overview',
  templateUrl: './depot-overview.component.html',
  styleUrls: ['./depot-overview.component.scss']
})
export class DepotOverviewComponent implements OnInit {

  holdings: IHolding[];
  absoluteChange: number;
  relativeChange: number;
  totalEarnings: number;

  constructor(private depotService: DepotService) {
  }

  ngOnInit() {
    this.depotService.currentDepot.subscribe((depot: IDepot) => {
      this.depotService.getAllHoldings(depot.id).subscribe(data => {
        this.holdings = data;
        this.calculateAbsoluteChange();
        this.calculateRelativeChange();
        this.calculateTotalEarnings();
      });
    });
  }

  calculateAbsoluteChange() {
    let absolutChange = 0;
    this.holdings.forEach(holding => {
      absolutChange += holding.amount * holding.stock.price;
      absolutChange -= holding.totalPrice;
    });
    this.absoluteChange = absolutChange;
  }

  calculateRelativeChange() {
    let holdingValue = 0;
    this.holdings.forEach(holding => {
      holdingValue += holding.totalPrice;
    });
    this.relativeChange = this.absoluteChange / holdingValue * 100;
  }

  calculateTotalEarnings() {
    let totalEarnings = 0;
    this.holdings.forEach(holding => {
      holding.earnings.forEach(earning => {
        totalEarnings += earning.earnings;
      })
    });
    this.totalEarnings = totalEarnings;
  }
}
