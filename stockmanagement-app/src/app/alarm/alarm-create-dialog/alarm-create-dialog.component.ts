import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {IStock} from "../../model/IStock";

@Component({
  selector: 'app-alarm-create-dialog',
  templateUrl: './alarm-create-dialog.component.html',
  styleUrls: ['./alarm-create-dialog.component.scss']
})
export class AlarmCreateDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AlarmCreateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: AlarmCreateData) {
  }

  ngOnInit() {

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isPriceValid() {
    return this.data.alarmPrice > 0;
  }

}

export interface AlarmCreateData {
  stock: IStock;
  alarmPrice: number;
}
