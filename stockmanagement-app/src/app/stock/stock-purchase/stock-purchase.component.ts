import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ISettings} from "../../model/ISettings";
import {SettingsService} from "../../services/settings/settings.service";

@Component({
  selector: 'app-stock-purchase',
  templateUrl: './stock-purchase.component.html',
  styleUrls: ['./stock-purchase.component.scss']
})
export class StockPurchaseComponent implements OnInit {
  readonly initialTotalPrice: number;
  settings: ISettings | any;

  constructor(
    public dialogRef: MatDialogRef<StockPurchaseComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public settingsService: SettingsService) {
    this.initialTotalPrice = this.data.totalPrice;
  }

  ngOnInit() {
    this.getSettings();
  }

  getSettings(){
    this.settingsService.getSettings().subscribe(
      data => {
        this.settings = data;
      }
    )
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isAmountValid(): boolean {
    return (this.data.amount > 0);
  }

  calculateTotalPrice() {
    if(this.initialTotalPrice*this.data.amount* ( 1 + this.settings.relativePurchaseCharges/100) > this.initialTotalPrice*this.data.amount + this.settings.flatPurchaseCharges) {
      this.data.totalPrice = this.initialTotalPrice * this.data.amount * (1 + this.settings.relativePurchaseCharges / 100);
    }else{
      this.data.totalPrice = this.initialTotalPrice * this.data.amount + this.settings.flatPurchaseCharges;
    }
  }
}
