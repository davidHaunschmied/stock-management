import { Injectable } from '@angular/core';
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IAlert} from "../../model/IAlert";

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  readonly endpoint = AppSettings.API_ENDPOINT + '/alerts';

  constructor(private http: HttpClient) { }

  getAllAlerts(): Observable<IAlert[]> {
    return this.http.get<IAlert[]>(this.endpoint + '/all', AppSettings.HTTP_OPTIONS);
  }

  createAlert(stock: string, value: number): Observable<IAlert> {
    return this.http.post<IAlert>(this.endpoint + '/new',
      {
        'stock': stock,
        'value': value,
      }, AppSettings.HTTP_OPTIONS);
  }
}
