import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-depot-create-dialog',
  templateUrl: './depot-create-dialog.component.html',
  styleUrls: ['./depot-create-dialog.component.scss']
})
export class DepotCreateDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DepotCreateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DepotCreateData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isNameValid(): boolean {
    return (3 <= this.data.name.length) && (this.data.name.length <= 30);
  }
}

export interface DepotCreateData {
  name: string;
}
