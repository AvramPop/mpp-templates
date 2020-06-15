import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {StudentComponent} from "./components/student/student.component";
import {SensorsComponent} from "./components/sensors/sensors.component";


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'all', component: StudentComponent},
  {path: 'names', component: SensorsComponent},
  // otherwise redirect to home
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
