import {Component, OnInit} from '@angular/core';
import {DepotService} from "./services/depot/depot.service";
import {MatDialog} from "@angular/material";
import {DepotDeleteDialogComponent} from './depots/depot-delete-dialog.component';
import {IDepot} from "./model/IDepot";
import {DepotImportDialogComponent} from "./depots/depot-import-dialog/depot-import-dialog.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  title = 'stockmanagement-app';
  depotPresent: boolean;
  private depot: IDepot;

  constructor(private depotService: DepotService,
              private dialog: MatDialog, private importDepotDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.depotService.currentDepot.subscribe(next => {
      if (next) {
        this.depotPresent = true;
        this.depot = next;
      } else {
        this.depotPresent = false;
      }
    });
  }

  openDeleteConfirmationDialog() {
    const dialogRef = this.dialog.open(DepotDeleteDialogComponent, {data: {depotName: this.depot.name}});

    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed && confirmed == true) {
        this.depotService.deleteDepot(this.depot).subscribe(any => {
            this.depotService.setCurrentDepot(null);
          }, error => {
            console.log('Error: ' + error.message);
          }
        );
      }
    });
  }

  openImportDialog() {
    const dialogRef = this.importDepotDialog.open(DepotImportDialogComponent, {
      width: '350px',
      data: {name: ''}
    });

    dialogRef.afterClosed().subscribe(data => {
      if (name == null)
        return;
      this.depotService.importDepot(data.name, data.file).subscribe(depot => {
        this.depotService.setCurrentDepot(depot);
      });
    });
  }
}
