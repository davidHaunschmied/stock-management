import { Component, OnInit } from '@angular/core';
import {ISettings} from "../model/ISettings";
import {SettingsService} from "../services/settings/settings.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  settings: ISettings;

  constructor(private settingsService: SettingsService) { }

  ngOnInit() {
    this.getSettings();
  }

  getSettings(){
    this.settingsService.getSettings().subscribe(data => {
      this.settings = data;
      });
  }

  changeSettings(){
    this.settingsService.changeSettings(this.settings).subscribe(data => {
      this.settings = data;
      });
  }

}
