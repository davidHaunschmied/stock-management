import {Component, OnInit} from '@angular/core';
import {DepotCreateDialogComponent} from "./depot-create-dialog.component";
import {DepotService} from "../services/depot/depot.service";
import {MatDialog} from "@angular/material";
import {IDepot} from "../model/IDepot";
import {FormControl} from "@angular/forms";

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
  filter = new FormControl();

  ngOnInit(): void {
    this.filter.setValue(this.depotService.getCurrentDepot());
    this.depotService.getAllDepots().subscribe(
      depots => {
        this.depots = depots;
      },
      error => {
        console.log('Error1: ' + error.message);
      }
    );
    console.log("Selected depot is: " + JSON.stringify(this.filter.value));
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

  refreshLocalStorageItem() {
    this.depotService.setCurrentDepot(this.filter.value);
  }

  compareDepots(d1: IDepot, d2: IDepot): boolean {
    return d1.id === d2.id;
  }
}
