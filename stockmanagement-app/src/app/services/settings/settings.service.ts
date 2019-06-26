import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../../app-settings";
import {ISettings} from "../../model/ISettings";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  readonly endpoint = AppSettings.API_ENDPOINT + '/settings';

  constructor(private http: HttpClient) { }

  getSettings(): Observable<ISettings>{
    return this.http.get<ISettings>(this.endpoint + '/all', AppSettings.HTTP_OPTIONS);
  }

  changeSettings(settings: ISettings): Observable<ISettings>{
    return this.http.post<ISettings>(this.endpoint + '/change',
      settings, AppSettings.HTTP_OPTIONS);
  }
}
