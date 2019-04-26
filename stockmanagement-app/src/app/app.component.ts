import {Component} from '@angular/core';
import {DepotService} from "./services/depot/depot.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private depotService: DepotService) {
  }

  title = 'stockmanagement-app';

  noDepotSelected(): boolean {
    return !this.depotService.getCurrentDepot();
  }
}
