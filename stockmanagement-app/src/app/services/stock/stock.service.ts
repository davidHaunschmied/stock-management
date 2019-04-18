import {Injectable} from '@angular/core';
import {IStock} from '../../model/IStock';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, tap, map} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {AppConstants} from "../../app-settings";

@Injectable({
    providedIn: 'root'
})
export class StockService {

    private stockUrl = AppConstants.API_ENDPOINT + '/stocks/stocks.json';

    constructor(private http: HttpClient) {
    }

    getStocks(): Observable<IStock[]> {
        return this.http.get<IStock[]>(this.stockUrl).pipe(
            tap(data => console.log('All: ' + JSON.stringify(data))),
            catchError(this.handleError)
        );
    }

    getStockDetails(name: string): Observable<IStock | undefined> {
        return this.getStocks().pipe(
            map((stocks: IStock[]) => stocks.find(s => s.name === name))
        );
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
