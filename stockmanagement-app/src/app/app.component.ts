import {Component, OnInit} from '@angular/core';
import {DepotService} from "./services/depot/depot.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  title = 'stockmanagement-app';
  depotPresent: boolean;

  constructor(private depotService: DepotService) {
  }

  ngOnInit(): void {
    this.depotService.currentDepot.subscribe(next => {
      if (next) {
        this.depotPresent = true;
      } else {
        this.depotPresent = false;
      }
    });
  }
}
