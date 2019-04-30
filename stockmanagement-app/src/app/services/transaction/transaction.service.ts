import {Injectable} from '@angular/core';
import {AppSettings} from "../../app-settings";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  readonly endpoint = AppSettings.API_ENDPOINT + '/transactions';

  constructor(private http: HttpClient) {
  }


  createTransaction(transaction: any) {
    // TODO

  }
}
