import {Component, OnInit} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {IAlarm} from "../model/IAlarm";

@Component({
  selector: 'app-alert-list',
  templateUrl: './alarm-list.component.html',
  styleUrls: ['./alarm-list.component.scss']
})
export class AlarmListComponent implements OnInit {

  alarms: IAlarm [];
  showAlarms: boolean;
  private stompClient;

  constructor() {
  }

  ngOnInit() {
    const ws = new SockJS('http://localhost:8080/alarms');
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/topic/alarm/notify", (message) => {
        that.alarms = JSON.parse(message.body);
      });
    });
  }

  getAlarmsCount() {
    if (this.alarms) {
      return this.alarms.length;
    }
    return 0;
  }

  toggleShowAlarms() {
    this.showAlarms = !this.showAlarms;
  }
}
