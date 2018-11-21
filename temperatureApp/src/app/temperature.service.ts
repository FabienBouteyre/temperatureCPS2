import { Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TemperatureService {

  private baseUrl = 'http://localhost:8080/api/temperature';

  constructor(private http: HttpClient) { }

  getTemperatureList(): Observable<any> {
	return this.http.get(`${this.baseUrl}`);
  }
}
