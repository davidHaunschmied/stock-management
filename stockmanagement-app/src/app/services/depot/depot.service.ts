import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IDepot} from '../../model/IDepot';
import {AppConstants} from '../../app-settings';

@Injectable({
    providedIn: 'root'
})
export class DepotService {

    readonly endpoint = AppConstants.API_ENDPOINT + '/depots';

    constructor(private http: HttpClient) {
    }

    getAllDepots(): Observable<IDepot[]> {
        return this.http.get<IDepot[]>(this.endpoint + '/all', AppConstants.HTTP_OPTIONS);
    }

    createDepot(name: string): Observable<IDepot> {
        return this.http.post<IDepot>(this.endpoint + '/new',
            {
                'name': name
            }, AppConstants.HTTP_OPTIONS);
    }
}
