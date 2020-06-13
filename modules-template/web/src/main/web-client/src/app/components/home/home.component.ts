import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isTeacher = false;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  redirectToStudents() {
    this.router.navigateByUrl("/students");
  }

  redirectToDisciplines() {
    this.router.navigateByUrl("/disciplines");
  }

}

