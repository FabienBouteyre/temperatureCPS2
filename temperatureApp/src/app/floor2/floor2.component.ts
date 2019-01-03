import { Component, enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { DxVectorMapModule } from 'devextreme-angular';
import { projection } from 'devextreme/viz/vector_map/projection';

import { FeatureCollection, Service } from '../temperature.service';

@Component({
  selector: 'app-floor2',
  providers: [ Service ],
  templateUrl: './floor2.component.html',
  styleUrls: ['./floor2.component.css']
})
export class Floor2Component {
  
  projection: any;
  roomsData: FeatureCollection;
  buildingData: FeatureCollection;
  
  constructor(service: Service) {
      this.roomsData = service.getRoomsDataFloor2();
      this.buildingData = service.getBuildingData();
      this.projection = projection({
          to: function (coordinates) {
              return [coordinates[0] / 100, coordinates[1] / 100];
          },

          from: function (coordinates) {
              return [coordinates[0] * 100, coordinates[1] * 100];
          }
      });
  }
}
