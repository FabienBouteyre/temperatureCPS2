import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { TemperatureService } from '../temperature.service';
import { Temperature } from '../temperature';
import { TemperatureDetailComponent } from '../temperature-detail/temperature-detail.component';



@Component({
  selector: 'app-floor1',
  templateUrl: './floor1.component.html',
  styleUrls: ['./floor1.component.css']
})
export class Floor1Component implements OnInit {

  temperatures: Observable<Temperature[]>;

  constructor(private temperatureService: TemperatureService) { }

  ngOnInit() {
  	this.reloadData();
  }

  reloadData(){
  	this.temperatures = this.temperatureService.getTemperatureList();
    console.log(this.temperatures[0]);
  }

}
