import {Component, OnInit} from '@angular/core';
import {Student} from "../../model/student";
import {StudentService} from "../../services/student/student.service";
import {MatTableDataSource} from "@angular/material/table";
import {Sensor} from "../../model/sensor";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  students: Student[];
  displayedColumns: string[] = ['id', 'name', 'measurement', 'time'];
  idToAdd: number;
  nameToAdd: string;
  idToDelete: number;
  idToUpdate: number;
  nameToUpdate: string;
  badAdd: boolean = false;
  badDelete: boolean = false;
  badUpdate: boolean = false;
  dataSource: any;
  nameToFilterBy: string;
  badFilterInput: boolean = false;
  filtered: boolean = false;
  studentsFiltered: Student[];

  constructor(private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.getSensors();
  }

  //
  // addStudent() {
  //   if (this.validAddData()) {
  //     const student = new Student(this.idToAdd, this.nameToAdd);
  //     this.studentService.saveStudent(student)
  //       .subscribe(response => {
  //         if (response == "404") {
  //           this.badAdd = true;
  //         } else {
  //           this.getStudents();
  //           this.badAdd = false;
  //         }
  //       })
  //   } else {
  //     this.badAdd = true;
  //   }
  // }
  //
  // deleteStudent() {
  //   if (this.validDeleteData()) {
  //     this.studentService.deleteStudent(this.idToDelete)
  //       .subscribe(response => {
  //         if (response == "404") {
  //           this.badDelete = true;
  //         } else {
  //           this.getStudents();
  //           this.badDelete = false;
  //         }
  //       })
  //   } else {
  //     this.badDelete = true;
  //   }
  // }


  getSensors(): void {
    this.studentService.getSensors()
      .subscribe(students => {
        this.dataSource = new MatTableDataSource<Sensor>(students);
      });
  }
  //
  // updateStudent() {
  //   if (this.validUpdateData()) {
  //     this.studentService.updateStudent(new Student(this.idToUpdate, this.nameToUpdate))
  //       .subscribe(response => {
  //         if (response == "404") {
  //           this.badUpdate = true;
  //         } else {
  //           this.getStudents();
  //           this.badUpdate = false;
  //         }
  //       })
  //   } else {
  //     this.badUpdate = true;
  //   }
  // }
  //
  // filterStudents() {
  //   if(this.validFilterData()) {
  //     this.studentService.getStudentsFiltered(this.nameToFilterBy).subscribe(
  //       result => {
  //         this.badFilterInput = false;
  //         this.filtered = true;
  //         this.studentsFiltered = result;
  //       }
  //     )
  //   } else {
  //     this.badFilterInput = true;
  //     this.filtered = false;
  //   }
  // }

  private validFilterData():boolean {
    return true;
  }

  private validAddData(): boolean {
    return true;
  }

  private validUpdateData(): boolean {
    return true;
  }

  private validDeleteData(): boolean {
    return true;
  }

}
