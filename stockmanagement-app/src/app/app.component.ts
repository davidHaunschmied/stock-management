import {Component, OnInit} from '@angular/core';
import {DepotService} from './services/depot.service';
import {IDepot} from './model/IDepot';
import {MatDialog} from '@angular/material';
import {DepotCreateDialogComponent} from './depots/depot-create-dialog.component';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    constructor(private depotService: DepotService,
                private createDepotDialog: MatDialog) {
    }

    title = 'stockmanagement-app';
    currentDepot: IDepot;
    depots: IDepot[] = [];

    ngOnInit(): void {
        this.depotService.getAllDepots().subscribe(
            depots => {
                this.depots = depots;
            },
            error => {
                console.log('Error: ' + error);
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
                    console.log('Error: ' + error);
                }
            );
        });
    }
}
