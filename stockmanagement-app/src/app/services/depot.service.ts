import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IDepot} from '../model/IDepot';
import {AppSettings} from '../app-settings';

@Injectable({
  providedIn: 'root'
})
export class DepotService {

  readonly current_depot_storage = 'CURRENT_DEPOT';

  readonly endpoint = AppSettings.API_ENDPOINT + '/depots';

  constructor(private http: HttpClient) {
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
    localStorage.setItem(this.current_depot_storage, JSON.stringify(depot));
  }

  getCurrentDepot(): IDepot {
    return JSON.parse(localStorage.getItem(this.current_depot_storage));
  }
}
