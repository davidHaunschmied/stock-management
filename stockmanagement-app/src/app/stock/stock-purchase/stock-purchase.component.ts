import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-stock-purchase',
  templateUrl: './stock-purchase.component.html',
  styleUrls: ['./stock-purchase.component.scss']
})
export class StockPurchaseComponent implements OnInit {
  readonly initialTotalPrice: number;

  constructor(
    public dialogRef: MatDialogRef<StockPurchaseComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.initialTotalPrice = this.data.totalPrice;
  }

  ngOnInit() {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  calculateTotalPrice() {
    this.data.totalPrice = this.initialTotalPrice * this.data.amount;
  }
}
