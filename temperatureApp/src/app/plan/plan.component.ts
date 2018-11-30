/*import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.css']
})
export class PlanComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}*/

import { NgModule, Component, enableProdMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { DxVectorMapModule } from 'devextreme-angular';
import { projection } from 'devextreme/viz/vector_map/projection';

import { FeatureCollection, Service } from '../temperature.service';

if(!/localhost/.test(document.location.host)) {
    enableProdMode();
}

@Component({
    selector: 'plan',
    providers: [ Service ],
    templateUrl: './plan.component.html',
    styleUrls: ['./plan.component.css']
})

export class PlanComponent {
    projection: any;
    roomsData: FeatureCollection;
    buildingData: FeatureCollection;
    
    constructor(service: Service) {
        this.roomsData = service.getRoomsData();
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

    customizeTooltip(arg) {
        if(arg.layer.name === "rooms")
            return {
                text: "Square: " + arg.attribute("square") + " ft&#178"
            };
    }
}

//platformBrowserDynamic().bootstrapModule(PlanComponent);
