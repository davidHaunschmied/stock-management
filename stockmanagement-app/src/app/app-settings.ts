import {HttpHeaders} from '@angular/common/http';

export class AppSettings {
  //  public static API_ENDPOINT = 'mockapi';
 public static API_ENDPOINT = 'http://localhost:8080/api';

  public static HTTP_OPTIONS = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': 'http://localhost:8080',
      'Content-Type': 'application/json'
    })
  };
}
