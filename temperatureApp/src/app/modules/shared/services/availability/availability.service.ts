import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AvailabilityService {
  private baseUrl = 'http://localhost:8080/api/availability';

  constructor(private http: HttpClient) {}

  getAvailability(date): Observable<any> {
    return this.http.get(`${this.baseUrl}` + `?datetime=` + date);
  }
}
