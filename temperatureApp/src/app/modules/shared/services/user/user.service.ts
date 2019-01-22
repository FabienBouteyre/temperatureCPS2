import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Temperature } from '../temperature/temperature';

export interface User {
  id: number;
  active: number;
  email: string;
  name: string;
  pass: string;
  surn: string;
  roles: UserRole;
}
export interface UserRole {
  user_id: number;
  roles: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getUser() {
    return this.http.get<User[]>(`${this.baseUrl}` + `/users/userList`);
  }

  addUser(newUser) {
    return this.http.post(`${this.baseUrl}/users/user/add`, newUser);
  }

  delUser(id) {
    return this.http.get(`${this.baseUrl}/users/user/delete/` + id);
  }

  editUser(edit) {
    return this.http.post(`${this.baseUrl}/users/user/edit/` + edit.id, edit);
  }

  deactivateUser(id) {
    return this.http.get(`${this.baseUrl}/users/user/desactivate/` + id);
  }

  activateUser(id) {
    return this.http.get(`${this.baseUrl}/users/user/activate/` + id);
  }

  setUser(id) {
    return this.http.get(`${this.baseUrl}/users/user/setUser/` + id);
  }

  setAdmin(id) {
    return this.http.get(`${this.baseUrl}/users/user/setAdmin/` + id);
  }
}
