import {Injectable} from '@angular/core';
import {IStock} from '../../model/IStock';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, tap, map} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {AppSettings} from "../../app-settings";

@Injectable({
  providedIn: 'root'
})
export class StockService {

  private endpoint = AppSettings.API_ENDPOINT + '/stocks';

  constructor(private http: HttpClient) {
  }

  getAllStocks(): Observable<IStock[]> {
    return this.http.get<IStock[]>(this.endpoint + '/all', AppSettings.HTTP_OPTIONS);
  }

  getStockDetails(id: number): Observable<IStock> {
    return this.http.get<IStock>(this.endpoint + '/stock-detail/' + id, AppSettings.HTTP_OPTIONS);
  }

  // Copy & Paste of DeborahK
  private handleError(err: HttpErrorResponse) {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}
