export class Sensor {
  id: number;
  name: string;
  measurement: number;
  time: string

  constructor(id: number, name: string, measurement: number, time: string) {
    this.id = id;
    this.name = name;
    this.measurement = measurement;
    this.time = time;
  }
}
