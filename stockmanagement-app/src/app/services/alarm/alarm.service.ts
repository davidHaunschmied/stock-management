import {Injectable} from '@angular/core';
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IAlarm} from "../../model/IAlarm";
import {IStock} from "../../model/IStock";

@Injectable({
  providedIn: 'root'
})
export class AlarmService {

  readonly endpoint = AppSettings.API_ENDPOINT + '/alarm';

  constructor(private http: HttpClient) {
  }

  getAllAlarms(): Observable<IAlarm[]> {
    return this.http.get<IAlarm[]>(this.endpoint + '/all', AppSettings.HTTP_OPTIONS);
  }

  createAlarm(stock: IStock, alarmPrice: number): Observable<IAlarm> {
    return this.http.post<IAlarm>(this.endpoint + '/new',
      {
        'stock': stock,
        'alarmPrice': alarmPrice,
      }, AppSettings.HTTP_OPTIONS);
  }
}
