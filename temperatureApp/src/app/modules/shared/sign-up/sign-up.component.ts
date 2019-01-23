import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../services/user/user.service';
import { isObject } from 'util';

@Component({
  selector: 'app-signup',
  providers: [UserService],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  private newUser = { name: '', surn: '', email: '', pass: '' };

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private userService: UserService
  ) {
    this.authService.setSignUpSuccessful(false);
    this.authService.setSignUpUnsuccessful(false);
  }

  ngOnInit() {
    this.authService.setLoginUnsuccessful(false);
  }

  signUp() {
    this.userService.addUser(this.newUser).subscribe(data => {
      
      if (data) {
        this.authService.setSignUpSuccessful(true);
        this.router.navigateByUrl('/login');
        this.newUser.name = '';
        this.newUser.surn = '';
        this.newUser.email = '';
        this.newUser.pass = '';
      } else {
        this.authService.setSignUpUnsuccessful(true);
        this.router.navigateByUrl('/sign-up');
      }
    });
  }

  cancel() {
    this.router.navigateByUrl('/');
  }
}
