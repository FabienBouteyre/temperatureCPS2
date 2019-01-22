import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Floor1Component } from '../modules/pages/floor1/floor1.component';
import { Floor2Component } from '../modules/pages/floor2/floor2.component';
import { AdminComponent } from '../modules/pages/admin/admin.component';
import { AuthGuard } from '../modules/shared/services/auth/auth-guard.service';
import { LoginComponent } from '../modules/shared/login/login.component';
import { AdviceComponent } from '../modules/pages/advice/advice.component';
import { SignUpComponent } from '../modules/shared/sign-up/sign-up.component';

const routes: Routes = [
  { path: 'floor1', canActivate: [AuthGuard], component: Floor1Component },
  { path: 'floor2', canActivate: [AuthGuard], component: Floor2Component },
  { path: 'advice', canActivate: [AuthGuard], component: AdviceComponent },
  { path: 'admin', canActivate: [AuthGuard], component: AdminComponent },
  { path: 'login', component: LoginComponent },
  { path: 'sign-up', component: SignUpComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
