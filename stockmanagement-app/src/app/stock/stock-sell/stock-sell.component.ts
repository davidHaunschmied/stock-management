import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {SettingsService} from "../../services/settings/settings.service";
import {ISettings} from "../../model/ISettings";

@Component({
  selector: 'app-stock-sell',
  templateUrl: './stock-sell.component.html',
  styleUrls: ['./stock-sell.component.scss']
})
export class StockSellComponent implements OnInit {
  private settings: ISettings;
  totalPrice: number;

  constructor(
    public dialogRef: MatDialogRef<StockSellComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public settingsService: SettingsService) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit() {
    this.settingsService.getSettings().subscribe(
      data => {
        this.settings = data;
        this.calculateTotalPrice();
      }
    )
  }

  isAmountValid(): boolean {
    return (this.data.amount > 0);
  }

  calculateTotalPrice() {
    if (this.data.holding.stock.price * this.data.amount * (1 + this.settings.relativeSellCharges / 100) > this.data.holding.stock.price * this.data.amount + this.settings.flatSellCharges) {
      this.totalPrice = this.data.holding.stock.price * this.data.amount * (1 - this.settings.relativeSellCharges / 100);
    } else {
      this.totalPrice = this.data.holding.stock.price * this.data.amount - this.settings.flatSellCharges;
    }
  }
}
