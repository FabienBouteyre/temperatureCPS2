import { Component,  OnInit, Input, ViewChild  } from '@angular/core';
import { projection } from 'devextreme/viz/vector_map/projection';
import { Observable } from 'rxjs';
import { Temperature } from '../temperature';
import { FeatureCollection, Feature, TemperatureService } from '../temperature.service';

import { DxVectorMapModule } from 'devextreme-angular';
import { text } from '@angular/core/src/render3';
import { delay } from 'q';

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
    //this.customizeLayers = this.customizeLayers.bind(this);
    this.buildingData = this.temperatureService.getBuildingData();
    this.projection = projection({
      to: function (coordinates) {
        return [coordinates[0] / 100, coordinates[1] / 100];
      }, from: function (coordinates) {
              return [coordinates[0] * 100, coordinates[1] * 100];
          }
    });
    this.temperatureService.getTemperature().subscribe(data => {
      this.listeTemperatures = data
      })
      console.log(this.listeTemperatures)
    setInterval(() => {
      this.temperatureService.getTemperature().subscribe(data => {
      this.listeTemperatures = data
      this.customizeLayers = this.customizeLayers.bind(this);
      console.log(this.listeTemperatures)
      //console.log(this.getValue("Room 101"))
      }) 
      }, 3000);
  }

  getValue(room: string): string{
    for(var i=0;this.listeTemperatures.length;i++){
        if(this.listeTemperatures[i].room==room){
          return this.listeTemperatures[i].sensorDataEntity.temp;
        }
    }
    return ""
  }

  customizeTooltip(arg) {
    if(arg.layer.name === "rooms")
        return {
            text: arg.attribute("name")
        };
  
  }
  customizeLayers(elements) {

    console.log("yo")
    elements.forEach((element) => {
      
      // if(element.attribute("name")=="Room 101" && this.listeTemperatures!=undefined){
      //   element.attribute("value", this.getValue("Room 101"));
      // }
      if(element.attribute("name")=="Room 101"){
        element.attribute("value", "10");
      }

      
      if(parseInt(element.attribute("value"))>30){
        element.applySettings({
          color: "#B22222"
      });
      
      } else if (parseInt(element.attribute("value"))<15){
        element.applySettings({
          color: "#4169E1"
      });
      } else if (parseInt(element.attribute("value"))>=15 || parseInt(element.attribute("value"))<=30) {
        element.applySettings({
          color: "#008000"
      });
      }
    
  });
    
  }
}
