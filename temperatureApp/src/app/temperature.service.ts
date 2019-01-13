import { Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Temperature {
    id: number,
    describ: string,
    sensorDataEntity: SensorDataEntity,
    room: string
}
interface SensorDataEntity {
    id: number,
    date: Date,
    temp: string,
    light: string,
    hmdt: string
}

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
    type: "FeatureCollection",
    features: [
        {
            type: "Feature",
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-56,70],
                    [-56,20],
                    [-25,20],
                    [-25,15],
                    [-61,15],
                    [-61,70],
                    [-175,70],
                    [-175,-70],
                    [-61,-70],
                    [-61,-4],
                    [-56,-4],
                    [-56,-70],
                    [21,-70],
                    [21,-4],
                    [26,-4],
                    [26,-70],
                    [175,-70],
                    [175,70],
                    [26,70],
                    [26,15],
                    [-12,15],
                    [-12,20],
                    [21,20],
                    [21,70],
                    [-56,70],
                    [-56,75],
                    [180,75],
                    [180,-75],
                    [-180,-75],
                    [-180,75],
                    [-56,75]
                ]]
            }
        }
    ]
};

let roomsDataFloor2: FeatureCollection = {
    type: "FeatureCollection",
    features: [
        {
            type: "Feature",
            properties: {
                name: "Room 201",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, 70],
                    [-130, 70],
                    [-130, 15],
                    [-175,15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 202",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-130, 70],
                    [-102, 70],
                    [-102, 15],
                    [-130, 15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 203",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-102, 70],
                    [-61, 70],
                    [-61, 15],
                    [-102, 15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 204",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-99, -4],
                    [-61, -4],
                    [-61, -70],
                    [-99, -70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 205",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-99, -4],
                    [-138, -4],
                    [-138, -70],
                    [-99, -70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 206",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, -25],
                    [-138,-25],
                    [-138,-70],
                    [-175,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, -25],
                    [-149,-25],
                    [-149,-20],
                    [-175,-20]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, -4],
                    [-149,-4],
                    [-149,-20],
                    [-175,-20]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, -4],
                    [-149,-4],
                    [-149,15],
                    [-175,15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 207",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-56, -23],
                    [21,-23],
                    [21,-70],
                    [-56, -70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 208",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [26,-4],
                    [62,-4],
                    [62,-70],
                    [26,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 209",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [62,-4],
                    [101,-4],
                    [101,-70],
                    [62,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 210",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [101,-4],
                    [125,-4],
                    [125,-70],
                    [101,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 211",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [125,-4],
                    [175,-4],
                    [175,-70],
                    [125,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [152,13],
                    [175,13],
                    [175,-4],
                    [152,-4]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [175,37],
                    [152,37],
                    [152,13],
                    [175,13]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 212",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [26,70],
                    [83,70],
                    [83,15],
                    [26,15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 213",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [83,70],
                    [144,70],
                    [144,15],
                    [83,15]
                ]]
            }
        }

    ]
};

let roomsDataFloor1: FeatureCollection = {
    type: "FeatureCollection",
    features: [
        {
            type: "Feature",
            properties: {
                name: "Room 101",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, 70],
                    [-115, 70],
                    [-115, 15],
                    [-175,15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 102",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-115, 70],
                    [-61, 70],
                    [-61, 15],
                    [-115, 15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 103",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175, -4],
                    [-61,-4],
                    [-61,-70],
                    [-175,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [-175,15],
                    [-142,15],
                    [-142,-4],
                    [-175, -4]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 104",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [26,-4],
                    [99,-4],
                    [99,-70],
                    [26,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 105",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [99,-4],
                    [175,-4],
                    [175,-70],
                    [99,-70]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 106",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [26,70],
                    [83,70],
                    [83,15],
                    [26,15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "Room 107",
                value: "?"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [83,70],
                    [143,70],
                    [143,15],
                    [83,15]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [150,15],
                    [175,15],
                    [175,-4],
                    [150,-4]
                ]]
            }
        }, {
            type: "Feature",
            properties: {
                name: "WC",
                value: "WC"
            },
            geometry: {
                type: "Polygon",
                coordinates: [[
                    [150,25],
                    [175,25],
                    [175,15],
                    [150,15]
                ]]
            }
        }
    ]
}


@Injectable({
    providedIn: 'root'
  })
export class TemperatureService {
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

    constructor(private http: HttpClient) { }

    getTemperature() {
	    return this.http.get<Temperature[]>(`${this.baseUrl}`+`/sensors/data/current`);
    }
}