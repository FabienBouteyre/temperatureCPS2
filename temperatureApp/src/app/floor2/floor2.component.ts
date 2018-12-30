import { Component, OnInit } from '@angular/core';
import { Service } from '../temperature.service';
import {
  BBox,
  Feature, FeatureCollection, GeometryCollection, LineString,
  MultiLineString, MultiPoint, MultiPolygon, Point, Polygon, GeometryObject
} from "geojson";
declare var require: any;

@Component({
  selector: 'app-floor2',
  providers: [ Service ],
  templateUrl: './floor2.component.html',
  styleUrls: ['./floor2.component.css']
})
export class Floor2Component implements OnInit {
  


  constructor() { 

  var parse = require('wellknown');
 
  var geojsonpoly = parse('POLYGON((101.23 171.82, 201.32 101.5, 215.7 201.953, 101.23 171.82))');

  console.log(geojsonpoly);
  }

  ngOnInit() {
  }
  

}
