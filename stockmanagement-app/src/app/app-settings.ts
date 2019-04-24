import {HttpHeaders} from '@angular/common/http';
import {IDepot} from "./model/IDepot";

export class AppSettings {
  // public static API_ENDPOINT = 'mockapi';
  public static API_ENDPOINT = 'http://localhost:8080/api';

    public static HTTP_OPTIONS = {
        headers: new HttpHeaders({
            'Access-Control-Allow-Origin': 'http://localhost:8080',
            'Content-Type': 'application/json'
        })
    };

  private static CURRENT_DEPOT_STORAGE_ITEM = 'CURRENT_DEPOT';

  public static setCurrentDepot(depot: IDepot) {
    localStorage.setItem(this.CURRENT_DEPOT_STORAGE_ITEM, JSON.stringify(depot));
  }

  public static getCurrentDepot(): IDepot {
    return JSON.parse(localStorage.getItem(this.CURRENT_DEPOT_STORAGE_ITEM));
  }
}
