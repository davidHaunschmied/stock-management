import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-alarm-create-dialog',
  templateUrl: './alarm-create-dialog.component.html',
  styleUrls: ['./alarm-create-dialog.component.scss']
})
export class AlarmCreateDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AlarmCreateDialogComponent>,
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
  name: string;
  price: number;
  above: boolean;
}
