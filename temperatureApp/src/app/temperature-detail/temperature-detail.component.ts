import { Component, OnInit, Input } from '@angular/core';

import { TemperatureService } from '../temperature.service';
import { Temperature } from '../temperature';

import { Floor1Component } from '../floor1/floor1.component';


@Component({
  selector: 'temperature-detail',
  templateUrl: './temperature-detail.component.html',
  styleUrls: ['./temperature-detail.component.css']
})
export class TemperatureDetailComponent implements OnInit {
  @Input() temperature: Temperature;
  
  constructor(private temperatureService: TemperatureService) { }

  ngOnInit() {
  }

}
