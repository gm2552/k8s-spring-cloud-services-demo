import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocatorFormComponent } from './locator-form.component';

describe('LocatorFormComponent', () => {
  let component: LocatorFormComponent;
  let fixture: ComponentFixture<LocatorFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocatorFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocatorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
