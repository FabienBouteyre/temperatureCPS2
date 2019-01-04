export class Temperature {
    id: number;
    describ: string;
    sensorDataEntity: SensorDataEntity;
    room: string;
}

export class SensorDataEntity {
    id: number;
    date: Date;
    temp: string;
}