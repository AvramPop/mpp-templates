import { Injectable } from '@angular/core';
import {Student} from "../../model/student";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {map, tap} from "rxjs/operators";
import {ResponseDto} from "../../model/response";

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private url = 'http://localhost:8080/api/students';

  constructor(
    private http: HttpClient) {
  }

  saveStudent(student: Student): Observable<string> {
    console.log(student);
    return this.http.post<ResponseDto>(this.url + "/add", student, this.httpOptions)
      .pipe(
        map(response => response["message"])
      );
  }

  deleteStudent(idToDelete: number): Observable<string> {
    return this.http.delete<ResponseDto>(this.url + "/delete/" + idToDelete, this.httpOptions)
      .pipe(
        map(response => response["message"])
      );
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.url + "/all", this.httpOptions)
      .pipe(
        tap(result => console.log(result)),
        map(result => result['students']),
      );
  }

  updateStudent(student: Student): Observable<string> {
    return this.http.put<ResponseDto>(this.url + "/up", student, this.httpOptions)
      .pipe(
        map(response => response["message"])
      );
  }

  getStudentsFiltered(name: string): Observable<Student[]> {
    return this.http.get<Student[]>(this.url + "/filtered/" + name, this.httpOptions)
      .pipe(
        map(result => result['students']),
      );
  }
}
