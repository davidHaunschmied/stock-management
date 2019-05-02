import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-stock-sell',
  templateUrl: './stock-sell.component.html',
  styleUrls: ['./stock-sell.component.scss']
})
export class StockSellComponent implements OnInit {
  value = 0;
  readonly initialPrice: number;

  constructor(
    public dialogRef: MatDialogRef<StockSellComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.initialPrice = data.price;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit() {
  }

  calculateTotalPrice() {
    this.data.price = this.data.amount * this.initialPrice;
  }
}
