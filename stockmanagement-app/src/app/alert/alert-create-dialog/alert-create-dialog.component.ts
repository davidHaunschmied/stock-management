import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-alarm-create-dialog',
  templateUrl: './alert-create-dialog.component.html',
  styleUrls: ['./alert-create-dialog.component.scss']
})
export class AlertCreateDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AlertCreateDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: AlertCreateData) {
  }

  ngOnInit() {
  }

  onNoClick(): void {
    console.log('Test');
    this.dialogRef.close();
  }

}


export interface AlertCreateData {
  stock: string;
  value: number;
  above: boolean;
}
