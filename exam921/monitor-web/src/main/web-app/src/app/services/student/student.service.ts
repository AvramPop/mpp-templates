import { Injectable } from '@angular/core';
import {Student} from "../../model/student";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {map, tap} from "rxjs/operators";
import {ResponseDto} from "../../model/response";
import {Sensor} from "../../model/sensor";

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private url = 'http://localhost:8080/';

  constructor(
    private http: HttpClient) {
  }

  getSensors(): Observable<Sensor[]> {
    return this.http.get<Sensor[]>(this.url + "all", this.httpOptions)
      .pipe(
        tap(result => console.log(result)),
        map(result => result['sensors']),
      );
  }

  getSensorsForName(name: string): Observable<Sensor[]> {
    return this.http.get<Sensor[]>(this.url + "sensors/" + name, this.httpOptions)
      .pipe(
        tap(result => console.log(result)),
        map(result => result['sensors']),
      );
  }

  getAllNames(): Observable<string[]> {
    return this.http.get<string[]>(this.url + "names", this.httpOptions)
      .pipe(
        tap(result => console.log(result)),
        map(result => result['names']),
      );
  }

  stop(name: string) {
    return this.http.post<any>(this.url + "stop/" + name, {}, this.httpOptions);
  }



}
