import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { AtmLocations } from 'src/schemas/atm-location';
import {MapInfoWindow, MapMarker} from '@angular/google-maps';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {
  
  apiLoaded: Observable<boolean>;
  markerOptions: google.maps.MarkerOptions = {draggable: false};
  markerPositions: google.maps.LatLngLiteral[] = [];
  markerTitles: string = "";
  center: google.maps.LatLngLiteral = {lat: 24, lng: 12};
  zoom = 10;
  click = true;
  title: string = "";
  addr: string = "";
  city: string = "";
  state: string = "";
  zip: string = "";
  @ViewChild(MapInfoWindow) infoWindow: MapInfoWindow;



  constructor(httpClient: HttpClient) {
    console.log('API_KEY:' + environment.apiKey)
    
    
    this.apiLoaded = httpClient.jsonp('https://maps.googleapis.com/maps/api/js?key=' + environment.apiKey, 'callback')
        .pipe(
          map(() => true),
          catchError(() => of(false)),
        );
  }

  @Input() locations: AtmLocations[] = [];

  ngOnInit(): void {
    this.locations.forEach(obj =>
      this.markerPositions.push({lat: obj.coordinates.latitude, lng: obj.coordinates.longitude})
      )
    this.center = {lat: this.locations[0].coordinates.latitude, lng: this.locations[0].coordinates.longitude}
    }
    openInfoWindow(marker: MapMarker, index: number) {
      this.infoWindow.open(marker);
      this.title = this.locations[index].name
      this.addr = this.locations[index].addr
      this.city = this.locations[index].city
      this.state = this.locations[index].state
      this.zip = this.locations[index].postalCode
    }
    removeInfo(){
      this.infoWindow.close();
    }
}
