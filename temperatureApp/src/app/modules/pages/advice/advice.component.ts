import { Component, OnInit } from '@angular/core';
import { AdviceService } from './advice.service';
import { AdviceClass } from './advice';
import { Observable } from 'rxjs/index';
import { AvailabilityService } from '../../shared/services/availability/availability.service';
import { FeatureCollection, TemperatureService } from '../../shared/services/temperature/temperature.service';
import { Floor1Component } from '../floor1/floor1.component';
import { count } from 'rxjs/internal/operators';
import { templateSourceUrl } from '@angular/compiler';

@Component({
  selector: 'app-advice',
  providers: [AdviceService, TemperatureService, AvailabilityService],
  templateUrl: './advice.component.html',
  styleUrls: ['./advice.component.css']
})
export class AdviceComponent implements OnInit {
  private listAdvice: AdviceClass[];
  private listAllAdvice: AdviceClass[];
  private currentInformation;
  private weather;
  private dispo: Observable<any>;
  private date: String;
  private roomsData1: FeatureCollection;
  private roomsData2: FeatureCollection;
  private averageTempFloor1;
  private averageTempFloor2;

  constructor(
    public adviceService: AdviceService,
    private availabilityService: AvailabilityService,
    private temperatureService: TemperatureService
  ) {}

  ngOnInit() {
    this.adviceService.getActiveAdvice().subscribe(data => {
      this.listAdvice = data;
    });
    this.adviceService.getAllAdvice().subscribe(data => {
      this.listAllAdvice = data;
    });

    setInterval(() => {
      this.adviceService.getActiveAdvice().subscribe(data => {
        this.listAdvice = data;
      });
      this.adviceService.getAllAdvice().subscribe(data => {
        this.listAllAdvice = data;
      });
    }, 1000);

    this.adviceService.getOutsideTemperature().subscribe(weather => {
      this.weather = weather;
    });

    this.adviceService.getCurrentTemperature().subscribe(data => {
      this.currentInformation = data;
      let countFirstFloor = 0;
      let countSecondFloor = 0;
      let avFl1 = 0;
      let avFl2 = 0;

      this.currentInformation.forEach(sensor => {
        let floor = sensor.room.substr(0, 1);
        let tempRoom = sensor.sensorDataEntity.temp;
        if (floor == 1) {
          avFl1 = avFl1 + +tempRoom;
          countFirstFloor = countFirstFloor + 1;
        }
        if (floor == 2) {
          avFl2 = avFl2 + +tempRoom;
          countSecondFloor = countSecondFloor + 1;
        }
      });
      this.averageTempFloor1 = avFl1 / countFirstFloor;
      this.averageTempFloor2 = avFl2 / countSecondFloor;
    });
    var today = new Date();
    var dd = today.getDate().toString();
    var mm = (today.getMonth() + 1).toString(); //January is 0!
    var yyyy = today.getFullYear();
    var hh = today.getHours().toString();
    var min = today.getMinutes().toString();
    var ss = today.getSeconds().toString();
    if (+dd < 10) {
      dd = '0' + dd;
    }
    if (+mm < 10) {
      mm = '0' + mm;
    }
    if (+hh < 10) {
      hh = '0' + hh;
    }
    if (+min < 10) {
      min = '0' + min;
    }
    if (+ss < 10) {
      ss = '0' + ss;
    }
    this.date = yyyy + '-' + mm + '-' + dd + '-' + hh + ':' + min + ':' + ss;
    this.availabilityService.getAvailability(this.date).subscribe(data => {
      this.dispo = data;
    });

    this.roomsData1 = this.temperatureService.getRoomsDataFloor1();
    this.roomsData2 = this.temperatureService.getRoomsDataFloor2();

    setInterval(() => {
      this.algo1();
      this.algo2();
      this.algo3();
      this.algo4();
    }, 3000);
  }

  ackAdvice(id) {
    this.adviceService.ackAdvice(id).subscribe();
  }

  enableAdvice(advice: AdviceClass) {
    let exists = false;
    this.listAdvice.forEach(ad => {
      if (ad.description == advice.description) {
        exists = true;
      }
    });
    if (!exists) {
      //save in the database if it does not already exist
      advice.active = 1;
      this.adviceService.saveAdvice(advice).subscribe();
    }
    exists = false;
  }

  timeSpringSummer() {
    let now = new Date();
    let startString = 'March 21, ' + now.getFullYear().toString() + ' 00:00:00';
    let stopString = 'September 21, ' + now.getFullYear().toString() + ' 00:00:00';
    let startDate = new Date(startString);
    let stopDate = new Date(stopString);
    if (now > startDate && now < stopDate) {
      return true;
    } else {
      return false;
    }
  }

  algo1() {
    let advice = new AdviceClass();
    let differenceTemperature = 5;
    let tempOutside = this.weather.main.temp - 273;

    if (this.timeSpringSummer()) {
      this.currentInformation.forEach(sensor => {
        let tempRoom = sensor.sensorDataEntity.temp;
        let lightRoom = sensor.sensorDataEntity.light;
        if (+tempRoom - tempOutside > differenceTemperature) {
          //initiate the advice that has to be stored
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '1';
          advice.description = 'Close the window of room ' + sensor.room + ' to keep the room fresh';
          advice.date = new Date();
          advice.floor = sensor.room.substr(0, 1);
          advice.room = sensor.room;
          advice.light = lightRoom;
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          //save in the database if it does not already exist
          this.enableAdvice(advice);
        }
        if (tempOutside - +tempRoom > differenceTemperature) {
          //initiate the advice that has to be stored
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '1';
          advice.description = 'Close the window to keep the room ' + sensor.room + ' warm';
          advice.date = new Date();
          advice.floor = sensor.room.substr(0, 1);
          advice.room = sensor.room;
          advice.light = lightRoom;
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          //save in the database if it does not already exist
          this.enableAdvice(advice);
        }
      });
    }
  }

  algo2() {
    let advice = new AdviceClass();
    let tempOutside = this.weather.main.temp - 273;
    let differenceTemperature = 2;

    this.currentInformation.forEach(sensor => {
      let floor = sensor.room.substr(0, 1);
      let tempRoom = sensor.sensorDataEntity.temp;
      let lightRoom = sensor.sensorDataEntity.light;

      if (floor == '1') {
        if (+tempRoom - this.averageTempFloor1 > differenceTemperature) {
          //initiate the advice that has to be stored
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '2';
          advice.description = 'The room ' + sensor.room + ' is abnormally hot. Please check it.';
          advice.date = new Date();
          advice.room = sensor.room;
          advice.floor = sensor.room.substr(0, 1);
          advice.light = lightRoom;
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          //save in the database if it does not already exist
          this.enableAdvice(advice);
        }
        if (this.averageTempFloor1 - +tempRoom > differenceTemperature) {
          //initiate the advice that has to be stored
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '2';
          advice.description =
            'The room ' + sensor.room + ' is abnormally cold. Please check the window or switch on the heat.';
          advice.date = new Date();
          advice.room = sensor.room;
          advice.light = lightRoom;
          advice.floor = sensor.room.substr(0, 1);
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          //save in the database if it does not already exist
          this.enableAdvice(advice);
        }
      }

      if (floor == '2') {
        if (+tempRoom - this.averageTempFloor2 > differenceTemperature) {
          //initiate the advice that has to be stored
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '2';
          advice.description = 'The room ' + sensor.room + ' is abnormally hot. Please check it.';
          advice.date = new Date();
          advice.room = sensor.room;
          advice.light = lightRoom;
          advice.floor = sensor.room.substr(0, 1);
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          //save in the database if it does not already exist
          this.enableAdvice(advice);
        }

        if (this.averageTempFloor2 - +tempRoom > differenceTemperature) {
          //initiate the advice that has to be stored
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '2';
          advice.description =
            'The room ' + sensor.room + ' is abnormally cold. Please check the window or switch the heat.';
          advice.date = new Date();
          advice.room = sensor.room;
          advice.light = lightRoom;
          advice.floor = sensor.room.substr(0, 1);
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          //save in the database if it does not already exist
          this.enableAdvice(advice);
        }
      }
    });
  }

  algo3() {
    let advice = new AdviceClass();
    let tempOutside = this.weather.main.temp - 273;

    this.currentInformation.forEach(sensor => {
      let tempRoom = sensor.sensorDataEntity.temp;
      let weatherState = this.weather.weather[0].main;
      let lightRoom = sensor.sensorDataEntity.light;

      if (weatherState == 'Clear' && tempOutside > 25) {
        //initiate the advice that has to be stored
        advice.id = this.listAllAdvice.length + 1;
        advice.type = '4';
        advice.description =
          'The room ' +
          sensor.room +
          ' will be exposed to the sun today.It may become hotter because of the sun exposure. To avoid this, please shut down the shutters';
        advice.date = new Date();
        advice.room = sensor.room;
        advice.floor = sensor.room.substr(0, 1);
        advice.light = lightRoom;
        advice.roomTemperature = tempRoom;
        advice.outsideTemperature = tempOutside.toString();
        //save in the database if it does not already exist
        this.enableAdvice(advice);
      }
    });
  }

  algo4() {
    let advice = new AdviceClass();
    let minLSunnyThreshold = 80;
    let minLNotSunnyThreshold = 50;
    let tempOutside = this.weather.main.temp - 273;

    this.currentInformation.forEach(sensor => {
      let lightRoom = sensor.sensorDataEntity.light;
      let weatherState = this.weather.weather[0].main;
      let sensorRoom = sensor.room;

      let tempRoom = sensor.sensorDataEntity.temp;

      if (weatherState == 'Clear') {
        var notOccupied = true;
        this.dispo.forEach(room => {
          if (room == sensorRoom) {
            notOccupied = false;
          }
        });
        if (notOccupied == true && lightRoom > minLSunnyThreshold) {
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '5';
          advice.room = sensorRoom;
          advice.floor = sensor.room.substr(0, 1);
          advice.description = 'Warning it seems that nobody is in the room ' + sensor.room + ' and the light is on';
          advice.date = new Date();
          advice.light = lightRoom;
          advice.roomTemperature = tempRoom;
          advice.outsideTemperature = tempOutside.toString();
          this.enableAdvice(advice);
        }
        notOccupied = true;
      }
      if (
        weatherState == 'Clouds' ||
        weatherState == 'Thunderstorm' ||
        weatherState == 'Atmosphere' ||
        weatherState == 'Snow' ||
        weatherState == 'Rain' ||
        weatherState == 'Drizzle'
      ) {
        var notOccupied = true;
        this.dispo.forEach(room => {
          if (room == sensorRoom) {
            notOccupied = false;
          }
        });

        if (notOccupied == true && lightRoom > minLNotSunnyThreshold) {
          advice.id = this.listAllAdvice.length + 1;
          advice.type = '5';
          advice.date = new Date();
          advice.room = sensor.room;
          advice.floor = sensor.room.substr(0, 1);
          advice.description = 'Warning it seems that nobody is in the room ' + sensor.room + ' and the light is on';
          advice.light = lightRoom;
          advice.roomTemperature = sensor.temp;
          advice.outsideTemperature = tempOutside.toString();
          this.enableAdvice(advice);
        }
        notOccupied = true;
      }
    });
  }
}
