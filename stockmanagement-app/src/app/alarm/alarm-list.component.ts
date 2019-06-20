import {Component, OnInit} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {IAlarm} from "../model/IAlarm";
import {AppSettings} from "../app-settings";
import {AlarmService} from "../services/alarm/alarm.service";

@Component({
  selector: 'app-alert-list',
  templateUrl: './alarm-list.component.html',
  styleUrls: ['./alarm-list.component.scss']
})
export class AlarmListComponent implements OnInit {

  alarms: IAlarm [];
  showAlarms: boolean;
  private stompClient;

  constructor(private alarmService: AlarmService) {
  }

  ngOnInit() {
    this.alarmService.getAllAlarmsToFire().subscribe(data => {
      this.alarms = data;
    });
    this.stompClient = Stomp.over(new SockJS(AppSettings.ENDPOINT + '/alarms'));
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

  deleteAlarm(alarm: IAlarm) {
    console.log(alarm);
    this.alarmService.deleteAlarm(alarm.id).subscribe(alarms => {
      this.alarms = alarms;
    });
  }
}
