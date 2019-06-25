import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-depot-delete-dialog',
  templateUrl: './depot-delete-dialog.component.html',
  styleUrls: ['./depot-delete-dialog.component.scss']
})
export class DepotDeleteDialogComponent implements OnInit {
  private depotName: string;

  constructor(public dialogRef: MatDialogRef<DepotDeleteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data: any) {
    this.depotName = data.depotName;
  }

  ngOnInit() {
  }

}
