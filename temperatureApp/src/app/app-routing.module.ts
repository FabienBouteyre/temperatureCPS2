import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Floor1Component } from './floor1/floor1.component';
import { Floor2Component } from './floor2/floor2.component';
import { AppComponent } from './app.component';

const routes: Routes = [
	{ path: 'floor1', component: Floor1Component},
	{ path: 'floor2', component: Floor2Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
