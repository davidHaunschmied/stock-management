import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-stock-sell',
  templateUrl: './stock-sell.component.html',
  styleUrls: ['./stock-sell.component.scss']
})
export class StockSellComponent implements OnInit {
  value = 0;

  constructor(
    public dialogRef: MatDialogRef<StockSellComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit() {
  }

}
