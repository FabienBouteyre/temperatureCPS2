import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { DxVectorMapModule } from 'devextreme-angular';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { Floor1Component } from './floor1/floor1.component';
import { Floor2Component } from './floor2/floor2.component';
import { TemperatureDetailComponent } from './temperature-detail/temperature-detail.component';
import { HttpClientModule } from '@angular/common/http';
import { PlanComponent } from './plan/plan.component';

@NgModule({
  declarations: [
    AppComponent,
    Floor1Component,
    Floor2Component,
    TemperatureDetailComponent,
    PlanComponent
  ],
  imports: [
    BrowserModule,
    DxVectorMapModule,
    HttpClientModule,
    AppRoutingModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
