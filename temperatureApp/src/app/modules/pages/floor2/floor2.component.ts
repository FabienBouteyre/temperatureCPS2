import { Component, enableProdMode, OnInit } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { DxVectorMapModule } from 'devextreme-angular';
import { projection } from 'devextreme/viz/vector_map/projection';
import { Temperature } from '../../shared/services/temperature/temperature';
import { FeatureCollection, TemperatureService } from '../../shared/services/temperature/temperature.service';
import { AvailabilityService } from '../../shared/services/availability/availability.service';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-floor2',
  providers: [TemperatureService, AvailabilityService],
  templateUrl: './floor2.component.html',
  styleUrls: ['./floor2.component.css']
})
export class Floor2Component implements OnInit {
  private projection: any;
  private roomsData2: FeatureCollection;
  private buildingData: FeatureCollection;
  private listeTemperatures: Temperature[];
  private dispo: Observable<any>;
  private elem: any;
  private date: String;

  constructor(private temperatureService: TemperatureService, private availabilityService: AvailabilityService) {}

  ngOnInit() {
    this.roomsData2 = this.temperatureService.getRoomsDataFloor2();
    this.customizeLayers = this.customizeLayers.bind(this);
    this.customizeTooltip = this.customizeTooltip.bind(this);
    this.buildingData = this.temperatureService.getBuildingData();
    this.projection = projection({
      to: function(coordinates) {
        return [coordinates[0] / 100, coordinates[1] / 100];
      },
      from: function(coordinates) {
        return [coordinates[0] * 100, coordinates[1] * 100];
      }
    });

    setInterval(() => {
      this.temperatureService.getTemperature().subscribe(data => {
        this.listeTemperatures = data;
      });
      this.customizeLayers(this.elem);
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
    }, 1000);
  }

  getValue(room: string): string {
    try {
      for (var i = 0; this.listeTemperatures.length; i++) {
        if (this.listeTemperatures[i].room == room) {
          return this.listeTemperatures[i].sensorDataEntity.temp;
        }
      }
    } catch (error) {
      return error;
    }
  }
  getLight(room: string): string {
    try {
      for (var i = 0; this.listeTemperatures.length; i++) {
        if (this.listeTemperatures[i].room == room) {
          return this.listeTemperatures[i].sensorDataEntity.light;
        }
      }
    } catch (error) {
      return error;
    }
  }
  getHmdt(room: string): string {
    try {
      for (var i = 0; this.listeTemperatures.length; i++) {
        if (this.listeTemperatures[i].room == room) {
          return this.listeTemperatures[i].sensorDataEntity.hmdt;
        }
      }
    } catch (error) {
      return error;
    }
  }
  getDescrib(room: string): string {
    for (var i = 0; this.listeTemperatures.length; i++) {
      if (this.listeTemperatures[i].room == room) {
        return this.listeTemperatures[i].describ;
      }
    }
    return '';
  }

  customizeTooltip(arg) {
    if (arg.layer.name === 'rooms') {
      if (this.listeTemperatures != undefined) {
        try {
          return {
            html:
              'Temperature : ' +
              this.getValue(arg.attribute('name')) +
              '°C' +
              '<br>' +
              'Humidity : ' +
              this.getHmdt(arg.attribute('name')) +
              '<br>' +
              'Light : ' +
              this.getLight(arg.attribute('name')) +
              '<br>' +
              '<br>' +
              this.getDescrib(arg.attribute('name'))
          };
        } catch (error) {
          return {
            html: 'No information'
          };
        }
      }
    }
  }
  customizeLayers(elements) {
    this.elem = elements;

    elements.forEach(element => {
      if (this.dispo != undefined) {
        if (element.attribute('name') == 'WC') {
          element.applySettings({
            color: '#C0C0C0'
          });
        }
        this.dispo.forEach(room => {
          if (element.attribute('name') == room.room) {
            element.applySettings({
              color: '#B22222'
            });
          }
        });
      }
    });
  }
}
