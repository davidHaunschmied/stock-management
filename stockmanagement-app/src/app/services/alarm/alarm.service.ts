import {Injectable} from '@angular/core';
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IAlarm} from "../../model/IAlarm";

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  readonly endpoint = AppSettings.API_ENDPOINT + '/alarm';

  constructor(private http: HttpClient) {
  }

  getAllAlerts(): Observable<IAlarm[]> {
    return this.http.get<IAlarm[]>(this.endpoint + '/all', AppSettings.HTTP_OPTIONS);
  }

  createAlert(stock: string, value: number): Observable<IAlarm> {
    return this.http.post<IAlarm>(this.endpoint + '/new',
      {
        'stock': stock,
        'value': value,
      }, AppSettings.HTTP_OPTIONS);
  }
}
