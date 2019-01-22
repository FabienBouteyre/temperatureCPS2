import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { TemperatureService } from '../services/temperature/temperature.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  providers: [TemperatureService],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = { email: '', password: '' };

  constructor(
    private temperatureService: TemperatureService,
    private authService: AuthService,
    private http: HttpClient,
    private router: Router
  ) {}

  login() {
    this.authService.authenticate(this.credentials, () => {
      if (this.authService.getLoginUnsuccessful()) {
        this.router.navigateByUrl('/login');
      } else {
        this.router.navigateByUrl('/');
      }
    });
    this.authService.setSignUpSuccessful(false);
    return false;
  }

  cancel() {
    this.router.navigateByUrl('/');
    this.authService.setSignUpSuccessful(false);
    this.authService.setLoginUnsuccessful(false);
  }

  logout() {
    this.authService.logout();
  }
}
