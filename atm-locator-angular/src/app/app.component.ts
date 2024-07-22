import { Component } from '@angular/core';
import {AtmLocatorApiSerivce} from '../services/atm-locator-api-serivce.service'
import { AtmLocations } from 'src/schemas/atm-location';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  results: AtmLocations[] = [];
  show: boolean = true;
  
  constructor() { }
  ngOnInit(): void {
  }

  getLocations(evt: AtmLocations[]) {
    this.results = evt;
    this.show = false;
  }

  showSearch() {
    this.show = true;
    this.results = [];
  }
}
