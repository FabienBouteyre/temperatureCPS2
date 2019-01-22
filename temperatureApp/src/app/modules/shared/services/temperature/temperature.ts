export class Temperature {
  id: number;
  describ: string;
  sensorDataEntity: SensorDataEntity;
  room: string;
  floor: string;
}

export class SensorDataEntity {
  id: number;
  date: Date;
  temp: string;
  light: string;
  hmdt: string;
}
