import {Component} from '@angular/core';
import {DepotService} from "./services/depot/depot.service";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  private stompClient;

  title = 'stockmanagement-app';

  noDepotSelected(): boolean {
    return !this.depotService.getCurrentDepot();
  }

  constructor(private depotService: DepotService) {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    const ws = new SockJS('http://localhost:8080/alarms');
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/topic/alarm/notify", (message) => {
        console.log(message);
      });
    });
  }
}
