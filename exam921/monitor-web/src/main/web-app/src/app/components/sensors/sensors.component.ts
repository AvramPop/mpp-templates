import { Component, OnInit } from '@angular/core';
import {StudentService} from "../../services/student/student.service";
import {Sensor} from "../../model/sensor";
import { TimerObservable } from "rxjs/observable/TimerObservable";
@Component({
  selector: 'app-sensors',
  templateUrl: './sensors.component.html',
  styleUrls: ['./sensors.component.css']
})
export class SensorsComponent implements OnInit {
  names: string[];
  sensors: Sensor[];
  showFlag = false;
  sensorData: Map<number, Sensor[]>;

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.sensorData = new Map<number, Sensor[]>();
    this.studentService.getAllNames()
      .subscribe(names => this.names = Array.from(new Set(names)));

    TimerObservable.create(0, 10000)
      .subscribe(() => this.updateData());

  }

  updateData(){

    this.studentService.getSensors()
      .subscribe(sensors => {
        for(let i = 0; i < sensors.length; i++) {
          this.studentService.getSensorsForName(sensors[i].name)
            .subscribe(result => this.sensorData.set(sensors[i].id, result));
        }
        this.sensors = sensors;
      });
  }

  show() {
    this.showFlag = true;

  }

  someMethod(name: string) {
    this.studentService.stop(name).subscribe();
  }

  toString(sensors: Sensor[]) {
    let result = "";
    for(let sensor of sensors) result += sensor.id + " " + sensor.name + " " + sensor.measurement + " " + sensor.time + "\n";
    return result;
  }
}
