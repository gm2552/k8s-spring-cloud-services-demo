# Local Development

The following scenario describes performing running the atm-location application on a developer's laptop.  It 
includes configuration and running local instances of Spring Cloud Gateway, Eureka, and Spring Cloud Config.

## Running Micros Services

The backend micro-services are written as Spring Boot applications, and each runs on its own server port.  
The atm-locator service is configured with the known ports of the downstream micro-services when running
without a service registry.  The front end service is written as an Angular single page application and communicates with the backend services
using a relative URL to its own base URL.

To run the Spring Boot microservices, run the following command from the root directory of each Spring Boot micro-service.

```
./mvnw spring-boot:run
```

To run the the front end service, run the following commands from the root of atm-locator-angular directory.
 **NOTE:**  The source code has a placehoder for the Google Maps API Key; you 
will need to update the `MY_GOOGLE_MAPS_API_KEY` planholder in the `atm-locator-angular/src/environment/environment.ts` file with
your own API key in order for the mapping feature to work. **NOTE:**  The source code has a placehoder for the Google Maps API Key; you 
will need to update the `MY_GOOGLE_MAPS_API_KEY` planholder in the `atm-locator-angular/src/environment/environment.prod.ts` file with
your own API key in order for the mapping feature to work.

```
npm install
ng serve
```

You can also run the services in your IDE using the appropriate run procedure.

## Running Local Spring Cloud Service Instances

The application has the ability to utilized Spring Cloud Services (SCS) for service registry and configuration data.  To properly
access the application locally via the Angular front end app, you also need to run an instace of Spring Cloud Gateway locally.

### Download Local Spring Cloud Service Jars

In order to run the SCS instances locally, you need to download their Jar files.  Follow these steps:

* Login to the Broadcom support [customer page](https://support.broadcom.com/group/ecx/my-dashboard)
* Click on `My Downloads` in the navigation bar on the left side of the screen.
* In the dropdown next to you user name in the upper right side of the screen, choose and select `Tanzu`
* In the product search box, type in `Spring Cloud Gateway for Kubernetes` 
* Select Spring Cloud Gateway for Kubernetes and choose the latest release.
* Download `Spring Cloud Gateway for Kubernetes Gateway executable jar file` and save it to the `local-services` directory of this repo.
* Download the `Spring Cloud Config Server` (SCC) [jar](https://packages.broadcom.com/artifactory/spring-enterprise/com/vmware/tanzu/spring/tanzu-config-server)
and save it to the `local-services` directory of this repo.  See these 
[instructions](https://docs.vmware.com/en/Tanzu-Spring-Runtime/Commercial/Tanzu-Spring-Runtime/config-server-install-config-server.html) for more information.

### Run Local Spring Cloud Services

The `local-services` directory contains preconfiguration for each of the Spring Cloud Services.  To run the services locally, launch the services by running
the following command fro the `local-services` directory

```
./runLocalServices.sh
```

You can terminate the local services by pressing `Control^C` on your keyboard.

### Connecting To Local Services

The Spring Cloud Gateway service is the main entry point into the application and the front end application can be access in your browser at 
[http://localhost:8080](http://localhost:8080).  The gateway is already pre-configured to route traffic to the angular frontend service
and the backend micro-services.

Backed end services are pre-configured to connect a local Spring Cloud Config Server at `localhost:8888`; the backend services will gracefully skip
reading from Spring Cloud Config if SCC is not running.

The backend services can discover each other using the local Eureka service (vs the hard coded port numbers in the `atm-locator` service), but require
that the Eureka profile be enabled on each backend service.  To connect to Eureka, launch each backend micro-service with the following command:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=eureka
```


