import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-depot-import-dialog',
  templateUrl: './depot-import-dialog.component.html',
  styleUrls: ['./depot-import-dialog.component.scss']
})
export class DepotImportDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DepotImportDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DepotImportData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isNameValid(): boolean {
    return (3 <= this.data.name.length) && (this.data.name.length <= 30) && this.data.file.name.endsWith(".csv");
  }

  ngOnInit(): void {
  }

  changeFile(files: FileList) {
    this.data.file = files.item(0);
  }
}

export interface DepotImportData {
  name: string;
  file: File;
}
