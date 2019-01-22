export class AdviceClass{

  id: number;
  type: String;
  date: Date;
  description: String;
  floor: String;
  light: String;
  roomTemperature: String;
  outsideTemperature: String;
  room: String;
  active: number;

}

export class SensorDataEntity {
  id: number;
  date: Date;
  temp: string;
  light: string;
  hmdt: string;

}
