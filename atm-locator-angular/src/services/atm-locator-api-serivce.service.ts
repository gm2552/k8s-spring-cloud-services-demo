import { Injectable } from '@angular/core';
import { ATMLOCATIONS } from 'src/mock-responses/mock-atm-location';
import { AtmLocations } from 'src/schemas/atm-location';
import { AtmSearch } from 'src/schemas/atm-search';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from '../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AtmLocatorApiSerivce {
  locations: AtmLocations[];
  private atmLocationApi = 'atmsearch'
  
  constructor(private http: HttpClient) { }

  fetchAtmLocations(location: AtmSearch): Observable<AtmLocations[]> {
    
    let queryParams = this.constructParams(location)

    if (!environment.production) {
      return of(ATMLOCATIONS);
    } else {
       return this.http.get<AtmLocations[]>(this.atmLocationApi, {params: queryParams})
    }
  }

  constructParams(location: AtmSearch) {
    let queryParams = new HttpParams();
  
    if (location.address) {
      queryParams = queryParams.append("address", location.address as string)
    } 
    if (location.city) {
      queryParams = queryParams.append("city", location.city as string)
    }
    if (location.state) {
      queryParams = queryParams.append("state", location.state as string)
    }
    if (location.postalCode) {
      queryParams = queryParams.append("postalCode", location.postalCode as string)
    }
    if (location.radius) {
      queryParams = queryParams.append("radius", location.radius as number)
    }
    if (location.latitude) {
      queryParams = queryParams.append("latitude", location.latitude as number)
    }
    if (location.longitude) {
      queryParams = queryParams.append("longitude", location.longitude as number)
    }
    return queryParams
  }
}
