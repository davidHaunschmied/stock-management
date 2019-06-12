import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material";
import {DepotImportDialogComponent} from "../depots/depot-import-dialog/depot-import-dialog.component";
import {DepotService} from "../services/depot/depot.service";
import {IDepot} from "../model/IDepot";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss']
})
export class ImportComponent implements OnInit {

  @Output()
  change: EventEmitter<IDepot> = new EventEmitter();

  constructor(private importDepotDialog: MatDialog, private depotService: DepotService) {
  }

  ngOnInit() {
  }

  openImportDialog(): void {
    const dialogRef = this.importDepotDialog.open(DepotImportDialogComponent, {
      width: '250px',
      data: {name: ''}
    });

    dialogRef.afterClosed().subscribe(data => {
      if (name == null)
        return;
      this.depotService.importDepot(data.name, data.file).subscribe(depot => {
        this.change.emit(depot);
      });
    });
  }
}
