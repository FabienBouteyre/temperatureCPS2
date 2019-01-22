import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './modules/shared/services/auth/auth.service';
import { AuthGuard } from './modules/shared/services/auth/auth-guard.service';

@Component({
  selector: 'app-root',
  providers: [], // AuthService, AuthGuard
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'temperatureApp';
  constructor(public authService: AuthService, public router: Router) {}

  public logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
