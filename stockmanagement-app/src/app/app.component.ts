import {Component, OnInit} from '@angular/core';
import {DepotService} from "./services/depot/depot.service";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  private stompClient;

  title = 'stockmanagement-app';
  depotPresent: boolean;

  constructor(private depotService: DepotService) {
    this.initializeWebSocketConnection();
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
