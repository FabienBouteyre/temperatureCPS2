import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignUpComponent } from './sign-up.component';
import { AuthGuard } from '../services/auth/auth-guard.service';
import { AuthService } from '../services/auth/auth.service';

const signUpRoutes: Routes = [{ path: 'sign-up', component: SignUpComponent }];

@NgModule({
  imports: [RouterModule.forChild(signUpRoutes)],
  exports: [RouterModule],
  providers: [AuthGuard, AuthService]
})
export class SignUpRoutingModule {}
