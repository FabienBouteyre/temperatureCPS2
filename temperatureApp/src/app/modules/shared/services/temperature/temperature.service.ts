import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Temperature } from './temperature';
import { Observable } from 'rxjs';

//For plan
export class FeatureCollection {
  type: string;
  features: Feature[];
}

export class Feature {
  type: string;
  properties?: FeatureProperty;
  geometry: FeatureGeometry;
}

export class FeatureProperty {
  name: string;
  value: string;
}

export class FeatureGeometry {
  type: string;
  coordinates: number[][][];
}

let buildingData: FeatureCollection = {
  type: 'FeatureCollection',
  features: []
};

let roomsDataFloor2: FeatureCollection = {
  type: 'FeatureCollection',
  features: []
};

let roomsDataFloor1: FeatureCollection = {
  type: 'FeatureCollection',
  features: []
};

let init = true;

@Injectable({
  providedIn: 'root'
})
export class TemperatureService {
  private buildings;
  private points;

  getBuildingData(): FeatureCollection {
    return buildingData;
  }
  getRoomsDataFloor2(): FeatureCollection {
    return roomsDataFloor2;
  }
  getRoomsDataFloor1(): FeatureCollection {
    return roomsDataFloor1;
  }

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
    if (init) {
      this.buildData(1).then(x =>
        x.subscribe(data => {
          this.buildings = data;

          for (let room of this.buildings) {
            this.buildDataGeometry(room.buildingGeometryId).then(c =>
              c.subscribe(coord => {
                this.points = coord;
                let tab = [];
                for (let p of this.points) {
                  tab = tab.concat([[p.x, p.y]]);
                }

                roomsDataFloor1.features = roomsDataFloor1.features.concat([
                  {
                    type: room.type,
                    properties: {
                      name: room.room,
                      value: ''
                    },
                    geometry: {
                      type: room.geometryType,
                      coordinates: [tab]
                    }
                  }
                ]);
              })
            );
          }
        })
      );

      this.buildData(2).then(x =>
        x.subscribe(data => {
          this.buildings = data;

          for (let room of this.buildings) {
            this.buildDataGeometry(room.buildingGeometryId).then(c =>
              c.subscribe(coord => {
                this.points = coord;
                let tab = [];
                for (let p of this.points) {
                  tab = tab.concat([[p.x, p.y]]);
                }

                roomsDataFloor2.features = roomsDataFloor2.features.concat([
                  {
                    type: room.type,
                    properties: {
                      name: room.room,
                      value: ''
                    },
                    geometry: {
                      type: room.geometryType,
                      coordinates: [tab]
                    }
                  }
                ]);
              })
            );
          }
        })
      );

      this.buildData(0).then(x =>
        x.subscribe(data => {
          this.buildings = data;

          for (let room of this.buildings) {
            this.buildDataGeometry(room.buildingGeometryId).then(c =>
              c.subscribe(coord => {
                this.points = coord;
                let tab = [];
                for (let p of this.points) {
                  tab = tab.concat([[p.x, p.y]]);
                }

                buildingData.features = buildingData.features.concat([
                  {
                    type: room.type,
                    geometry: {
                      type: room.geometryType,
                      coordinates: [tab]
                    }
                  }
                ]);
              })
            );
          }
        })
      );

      init = false;
    }
  }

  getTemperature() {
    return this.http.get<Temperature[]>(`${this.baseUrl}` + `/sensors/data/current`);
  }
  getAvailability(date): Observable<any> {
    return this.http.get(`${this.baseUrl}` + `/availability?datetime=` + date);
  }

  buildData(floor): Promise<Observable<any>> {
    return Promise.resolve(this.http.get(`${this.baseUrl}` + `/buildingByFloor?floor=` + floor));
  }

  buildDataGeometry(id): Promise<Observable<any>> {
    return Promise.resolve(this.http.get(`${this.baseUrl}` + `/buildingGeometryById?id=` + id));
  }
}
