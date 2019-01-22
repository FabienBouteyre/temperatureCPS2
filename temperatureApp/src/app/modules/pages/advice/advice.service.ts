import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdviceClass } from './advice';

@Injectable({
  providedIn: 'root'
})
export class AdviceService {
  constructor(public httpclient: HttpClient) {}

  private baseUrl = 'http://localhost:8080/api';
  getActiveAdvice() {
    return this.httpclient.get<AdviceClass[]>(`${this.baseUrl}` + `/advice/active`);
  }
  getAllAdvice() {
    return this.httpclient.get<AdviceClass[]>(`${this.baseUrl}` + `/advice`);
  }
  getCurrentTemperature() {
    return this.httpclient.get<AdviceClass[]>(`${this.baseUrl}` + `/sensors/data/current`);
  }

  getOutsideTemperature() {
    return this.httpclient.get(`${this.baseUrl}` + `/weather/city?city=Saint-Etienne`);
  }

  ackAdvice(id: number) {
    return this.httpclient.post(`${this.baseUrl}` + `/advice/active?id=` + id, id);
  }

  saveAdvice(advice: AdviceClass) {
    return this.httpclient.post(`${this.baseUrl}` + `/advice/add`, advice);
  }
}
