import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {IDepot} from "../../model/IDepot";
import {AppSettings} from "../../app-settings";

@Injectable({
  providedIn: 'root'
})
export class DepotService {

  readonly current_depot_storage = 'CURRENT_DEPOT';
  readonly endpoint = AppSettings.API_ENDPOINT + '/depots';

  public currentDepot;

  constructor(private http: HttpClient) {
    this.currentDepot = new BehaviorSubject<IDepot>(JSON.parse(localStorage.getItem(this.current_depot_storage)));
  }

  getAllDepots(): Observable<IDepot[]> {
    return this.http.get<IDepot[]>(this.endpoint + '/all', AppSettings.HTTP_OPTIONS);
  }

  createDepot(name: string): Observable<IDepot> {
    return this.http.post<IDepot>(this.endpoint + '/new',
      {
        'name': name
      }, AppSettings.HTTP_OPTIONS);
  }

  setCurrentDepot(depot: IDepot) {
    this.currentDepot.next(depot);
    localStorage.setItem(this.current_depot_storage, JSON.stringify(depot));
  }
}
