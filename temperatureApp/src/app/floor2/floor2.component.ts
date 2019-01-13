import { Component, enableProdMode, OnInit } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { DxVectorMapModule } from 'devextreme-angular';
import { projection } from 'devextreme/viz/vector_map/projection';
import { Temperature } from '../temperature';   
import { FeatureCollection, TemperatureService } from '../temperature.service';

@Component({
  selector: 'app-floor2',
  providers: [ TemperatureService ],
  templateUrl: './floor2.component.html',
  styleUrls: ['./floor2.component.css']
})
export class Floor2Component implements OnInit{
  
  private projection: any;
  private roomsData: FeatureCollection;
  private buildingData: FeatureCollection;
  private listeTemperatures: Temperature[];
  private elem:any;
  
  constructor(private temperatureService: TemperatureService) {}

  ngOnInit() {
    this.roomsData = this.temperatureService.getRoomsDataFloor2();
    this.customizeLayers = this.customizeLayers.bind(this);
    this.customizeTooltip = this.customizeTooltip.bind(this);
    this.buildingData = this.temperatureService.getBuildingData();
    this.projection = projection({
      to: function (coordinates) {
        return [coordinates[0] / 100, coordinates[1] / 100];
      }, from: function (coordinates) {
              return [coordinates[0] * 100, coordinates[1] * 100];
          }
    });
    
    setInterval(() => {  
      this.temperatureService.getTemperature().subscribe(data => {
      this.listeTemperatures = data;
      }); 
      this.customizeLayers(this.elem);
      }, 1000);
  }

  getValue(room: string): string{
    for(var i=0;this.listeTemperatures.length;i++){
        if(this.listeTemperatures[i].room==room){
          return this.listeTemperatures[i].sensorDataEntity.temp;
        }
    }
    return ""
  }
  getDescrib(room: string): string{
    for(var i=0;this.listeTemperatures.length;i++){
      if(this.listeTemperatures[i].room==room){
        return this.listeTemperatures[i].describ;
      }
    }
    return ""
  }

  customizeTooltip(arg) {
    if(arg.layer.name === "rooms"){
      if(this.listeTemperatures != undefined){
        try {
          return {
            text: arg.attribute("name")+"<br>"+this.getDescrib(arg.attribute("name"))
        };
        } catch (error) {
          return {
            html: arg.attribute("name")
        };
        }
      }
    }  
  }
  customizeLayers(elements) {
    this.elem = elements;

    elements.forEach((element) => {
      
      if(this.listeTemperatures != undefined){
        try {
          if (element.attribute("name")=="WC"){
            element.attribute("value","WC");
          } else {
            element.attribute("value", this.getValue(element.attribute("name")));
          }
        } catch (error) {
          element.attribute("value", "?");
        }
      }

      if(element.attribute("value") == "?" || element.attribute("value") == "WC"){
        element.applySettings({
          color: "#808080"
      });
      }
      else if(parseInt(element.attribute("value"))>30){
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
