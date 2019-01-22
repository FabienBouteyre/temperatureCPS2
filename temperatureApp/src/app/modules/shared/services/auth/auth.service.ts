import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay, tap } from 'rxjs/operators';
import { UserService } from '../user/user.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { isObject } from 'util';

@Injectable()
export class AuthService {
  isUser = false;
  isAdmin = false;
  private signUpSuccessful: Boolean;
  private signUpUnsuccessful: Boolean;
  private loginUnsuccessful: Boolean;

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(private http: HttpClient) {
    this.signUpSuccessful = false;
    this.signUpUnsuccessful = false;
    this.loginUnsuccessful = false;
  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(
      credentials
        ? {
            authorization: 'Basic ' + btoa(credentials.email + ':' + credentials.password)
          }
        : {}
    );

    this.http.get('http://localhost:8080/api/users/user/login/' + credentials.email).subscribe(response => {
      if (isObject(response)) {
        if (response['active']) {
          if (
            response['pass'] == credentials.password &&
            (response['roles'][0] == 'ADMIN' || response['roles'][1] == 'ADMIN')
          ) {
            this.isAdmin = true;
            this.isUser = true;
            this.setLoginUnsuccessful(false);
          } else if (response['pass'] == credentials.password && response['roles'][0] == 'USER') {
            this.isAdmin = false;
            this.isUser = true;
            this.setLoginUnsuccessful(false);
          } else {
            this.setLoginUnsuccessful(true);
          }
        } else {
          this.setLoginUnsuccessful(true);
        }
      } else {
        this.setLoginUnsuccessful(true);
      }
      return callback && callback();
    });
  }

  setSignUpSuccessful(value: Boolean) {
    this.signUpSuccessful = value;
  }

  getSignUpSuccessful() {
    return this.signUpSuccessful;
  }

  setSignUpUnsuccessful(value: Boolean) {
    this.signUpUnsuccessful = value;
  }

  getSignUpUnsuccessful() {
    return this.signUpUnsuccessful;
  }

  setLoginUnsuccessful(value) {
    this.loginUnsuccessful = value;
  }

  getLoginUnsuccessful() {
    return this.loginUnsuccessful;
  }

  logout(): void {
    this.isAdmin = false;
    this.isUser = false;
  }
}
