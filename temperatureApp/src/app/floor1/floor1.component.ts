import { Component,  OnInit, Input, ViewChild  } from '@angular/core';
import { projection } from 'devextreme/viz/vector_map/projection';
import { Observable } from 'rxjs';
import { Temperature } from '../temperature';
import { FeatureCollection, Feature, TemperatureService } from '../temperature.service';

import { DxVectorMapModule } from 'devextreme-angular';

@Component({
  selector: 'app-floor1',
  providers: [ TemperatureService ],
  templateUrl: './floor1.component.html',
  styleUrls: ['./floor1.component.css']
})
export class Floor1Component implements OnInit {

  private projection: any;
  private roomsData: FeatureCollection;
  private buildingData: FeatureCollection;
  private listeTemperatures: Temperature[];

  constructor(private temperatureService: TemperatureService) {}  
  
  ngOnInit() {
    this.roomsData = this.temperatureService.getRoomsDataFloor1();
    this.customizeLayers = this.customizeLayers.bind(this);
    this.customizeLabel = this.customizeLabel.bind(this);
    console.log(this.roomsData.features[0].properties.name);
    this.buildingData = this.temperatureService.getBuildingData();
    this.projection = projection({
      to: function (coordinates) {
        return [coordinates[0] / 100, coordinates[1] / 100];
      }, from: function (coordinates) {
              return [coordinates[0] * 100, coordinates[1] * 100];
          }
    });
    let temptemp = 1;
     setInterval(() => {
       //this.roomsDatabis.features[0].properties.name = this.roomsDatabis.features[0].properties.name+"k";
       let lables = document.getElementsByClassName("dxm-layer-labels")
       temptemp = temptemp + 1;

      //  console.log(lables.item(0).childNodes[0].childNodes[0].replaceWith(temptemp.toString()));
      //  console.log(lables.item(0).childNodes[0].childNodes[1].replaceWith(""));
       
       }, 2000);
  }

  customizeTooltip(arg) {
    if(arg.layer.name === "rooms")
        return {
            text: arg.attribute("name")
        };
  
  }
  customizeLabel = (arg: any) => {
    if (arg.value > 30) {
        return {
            visible: true,
            backgroundColor: "#ff7c7c",
            customizeText: function (e: any) {
                return e.valueText + "&#176F";
            }
        };
    }
}

customizeText = (arg: any) => {
    return arg.value + "&#176F";
}

customizeLayers(elements) {
  elements.forEach((element) => {
    element.applySettings({
      color: "#B22222"
  });
  });
  
}
  
}
