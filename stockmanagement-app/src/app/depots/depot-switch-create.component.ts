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
    this.depotService.currentDepot.subscribe(depot => {
      this.filter.setValue(depot);
      if (!depot){
        this.reloadDepots();
      }
    });
    this.reloadDepots();
  }

  reloadDepots() {
    this.depotService.getAllDepots().subscribe(
      depots => {
        this.depots = depots;
      },
      error => {
        console.log('Error: ' + error.message);
      }
    );
  }

  openCreateDepotDialog(): void {
    const dialogRef = this.createDepotDialog.open(DepotCreateDialogComponent, {
      width: '250px',
      data: {name: ''}
    });

    dialogRef.afterClosed().subscribe(name => {
      if (name == null)
        return;
      this.depotService.createDepot(name).subscribe(
        depot => {
          this.depots.push(depot);
        }, error => {
          console.log('Error: ' + error.message);
        }
      );
    });
  }

  refreshCurrentDepot() {
    this.depotService.setCurrentDepot(this.filter.value);
  }

  compareDepots(d1: IDepot, d2: IDepot): boolean {
    return (d1 && d2) && (d1.id === d2.id);
  }
}
