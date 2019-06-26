import { Component, OnInit } from '@angular/core';
import {ISettings} from "../model/ISettings";
import {SettingsService} from "../services/settings/settings.service";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  settings: ISettings;

  constructor(private settingsService: SettingsService,
              private _snackBar: MatSnackBar) { }

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
      this.openSnackBar(data);
      });
  }

  openSnackBar(data: ISettings) {
    //this._snackBar.open("Kauf: " + data.relativePurchaseCharges + "% (mind. € " + data.flatPurchaseCharges + ") / Verkauf: " + data.relativeSellCharges + "% (mind. € " + data.flatSellCharges + ") ", "Close", {
    this._snackBar.open("Erfolgreich gespeichert", "Schließen!", {
    duration: 3000,
    });
  }

}
