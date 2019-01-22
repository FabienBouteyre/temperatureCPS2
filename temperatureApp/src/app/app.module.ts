import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { DxVectorMapModule } from 'devextreme-angular';

import { AppRoutingModule } from './routing/app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { Floor1Component } from './modules/pages/floor1/floor1.component';
import { Floor2Component } from './modules/pages/floor2/floor2.component';
import { AdminComponent } from './modules/pages/admin/admin.component';
import { LoginComponent } from './modules/shared/login/login.component';
import { LoginRoutingModule } from './modules/shared/login/login-routing.module';
import { FormsModule } from '@angular/forms';
import { AdviceComponent } from './modules/pages/advice/advice.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SignUpRoutingModule } from './modules/shared/sign-up/sign-up-routing.module';
import { SignUpComponent } from './modules/shared/sign-up/sign-up.component';

@NgModule({
  declarations: [
    AppComponent,
    Floor1Component,
    Floor2Component,
    AdviceComponent,
    AdminComponent,
    LoginComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    DxVectorMapModule,
    HttpClientModule,
    AppRoutingModule,
    LoginRoutingModule,
    SignUpRoutingModule,
    FormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
