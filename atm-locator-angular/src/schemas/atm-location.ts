export interface AtmLocations {
    addr: string,
    branch: null | {
      addr: string,
      city: string,
      details: [
        string
      ],
      distance: number,
      hours: [
        {
          close: string,
          day: string,
          open: string
        }
      ],
      name: string,
      notes: [
        string?
      ],
      postalCode: string,
      state: string
    },
    city: string,
    coordinates: {
      latitude: number,
      longitude: number
    },
    details: [
      string?
    ],
    distance: number,
    id: number,
    inDoors: boolean,
    name: string,
    notes: [
      string?
    ],
    postalCode: string,
    state: string, 
  }