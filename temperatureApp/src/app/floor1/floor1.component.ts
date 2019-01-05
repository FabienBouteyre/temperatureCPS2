import { Component,  ViewChildren, QueryList  } from '@angular/core';
import { projection } from 'devextreme/viz/vector_map/projection';
import { Observable } from 'rxjs';
import { Temperature } from '../temperature';
import { FeatureCollection, TemperatureService } from '../temperature.service';


@Component({
  selector: 'app-floor1',
  providers: [ TemperatureService ],
  templateUrl: './floor1.component.html',
  styleUrls: ['./floor1.component.css']
})
export class Floor1Component {

  private projection: any;
  private roomsData: FeatureCollection;
  private buildingData: FeatureCollection;
  private listeTemperatures: Observable<Temperature[]>;
  
  constructor(private temperatureService: TemperatureService) {
      this.roomsData = temperatureService.getRoomsDataFloor1();
      this.buildingData = temperatureService.getBuildingData();
      this.projection = projection({
          to: function (coordinates) {
              return [coordinates[0] / 100, coordinates[1] / 100];
          },

          from: function (coordinates) {
              return [coordinates[0] * 100, coordinates[1] * 100];
          }
      });
      this.getValueTemperature();
  }

  getValueTemperature(){
    this.listeTemperatures = this.temperatureService.getTemperature();      
  }
}
