import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisciplineComponent } from './discipline.component';

describe('DisciplineComponent', () => {
  let component: DisciplineComponent;
  let fixture: ComponentFixture<DisciplineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisciplineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisciplineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
