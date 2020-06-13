import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {StudentComponent} from "./components/student/student.component";
import {DisciplineComponent} from "./components/discipline/discipline.component";


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'students', component: StudentComponent},
  {path: 'disciplines', component: DisciplineComponent},
  // otherwise redirect to home
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
