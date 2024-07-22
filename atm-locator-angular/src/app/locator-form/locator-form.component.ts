import { Component, EventEmitter, Output } from '@angular/core';
import {AtmLocatorApiSerivce} from '../../services/atm-locator-api-serivce.service'
import { AtmLocations } from 'src/schemas/atm-location';
import { FormGroup, FormControl, Validators } from '@angular/forms'

interface Radius {
  value: number;
}
@Component({
  selector: 'app-locator-form',
  templateUrl: './locator-form.component.html',
  styleUrls: ['./locator-form.component.scss']
})


export class LocatorFormComponent {

  @Output() sendLocations: EventEmitter<AtmLocations[]> = new EventEmitter; 

  locations: AtmLocations[];
  
  locationForm = new FormGroup({
    address: new FormControl(),
    postalCode: new FormControl(),
    city: new FormControl(),
    state: new FormControl(),
    radius: new FormControl()
  })

  radius: Radius[] = [
    {value: 10},
    {value: 20},
    {value: 30},
    {value: 40},
    {value: 50},
  ];

  constructor(private atmLocatorApiSerivce: AtmLocatorApiSerivce) { }

  onSubmit(location: object): void {
    this.atmLocatorApiSerivce.fetchAtmLocations(location)
    .subscribe(locations => 
      this.sendLocations.emit(locations)
      );
  }
}
