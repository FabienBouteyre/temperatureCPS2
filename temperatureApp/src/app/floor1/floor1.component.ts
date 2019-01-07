import { Component,  OnInit } from '@angular/core';
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
export class Floor1Component implements OnInit {

  private projection: any;
  private roomsData: FeatureCollection;
  private buildingData: FeatureCollection;
  private listeTemperatures: Temperature[];
  
  constructor(private temperatureService: TemperatureService) {}  
  
  ngOnInit() {
    this.roomsData = this.temperatureService.getRoomsDataFloor1();
    this.buildingData = this.temperatureService.getBuildingData();
    this.projection = projection({
      to: function (coordinates) {
        return [coordinates[0] / 100, coordinates[1] / 100];
      }, from: function (coordinates) {
              return [coordinates[0] * 100, coordinates[1] * 100];
          }
    });
    // this.temperatureService.getTemperature().subscribe(data => {
    //   this.listeTemperatures = data
    // })
    setInterval(() => {
      this.temperatureService.getTemperature().subscribe(data => {
      this.listeTemperatures = data
      }) 
      }, 1000);

    // this.listeTemperatures = new Observable(observer =>
    //   setInterval(() => observer.next(this.temperatureService.getTemperature()), 10000)
    // );
    //this.getValueTemperature();
  }

  // getValueTemperature(){
  //   this.listeTemperatures = this.temperatureService.getTemperature(); 
  //   console.log(this.listeTemperatures);     
  // }
  
}
