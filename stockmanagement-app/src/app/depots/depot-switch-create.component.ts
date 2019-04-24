import {Component, OnInit} from '@angular/core';
import {DepotCreateDialogComponent} from "./depot-create-dialog.component";
import {DepotService} from "../services/depot/depot.service";
import {MatDialog} from "@angular/material";
import {IDepot} from "../model/IDepot";
import {AppSettings} from "../app-settings";

@Component({
  selector: 'app-depot-switch-add',
  templateUrl: './depot-switch-create.component.html',
  styleUrls: ['./depot-switch-create.component.scss']
})
export class DepotSwitchCreateComponent implements OnInit {

  constructor(private depotService: DepotService,
              private createDepotDialog: MatDialog) {
  }

  depots: IDepot[] = [];

  ngOnInit(): void {
    this.depotService.getAllDepots().subscribe(
      depots => {
        this.depots = depots;
      },
      error => {
        console.log('Error1: ' + error.message);
      }
    );
  }

  openCreateDepotDialog(): void {
    const dialogRef = this.createDepotDialog.open(DepotCreateDialogComponent, {
      width: '250px',
      data: {name: ''}
    });

    dialogRef.afterClosed().subscribe(name => {
      console.log('The dialog was closed');
      this.depotService.createDepot(name).subscribe(
        depot => {
          this.depots.push(depot);
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  changeDepot(depot: IDepot) {
    AppSettings.setCurrentDepot(depot);
    console.log(JSON.stringify(AppSettings.getCurrentDepot()));
  }
}
