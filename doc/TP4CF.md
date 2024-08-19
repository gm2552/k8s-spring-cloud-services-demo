# Tanzu Platform For Cloud Foundry (TP4CF) Deployment

The atm-locator TP4CF deployment options are avialbe through the manifest.yml files for you.  You will
still be responsible for creating service instances, however the manifest files will include service binding configuration.  

## Prerequisites

* Tanzu Platform For Cloud Foundry (TP4CF)
* Spring Cloud Services for VMware Tanzu
* Spring Cloud Gateway for VMware Tanzu
* JDK 21+
* npm

## Quick Start

* Clone this repository

* Open a command shell and navigate to the root directory of this repository.  Run the following commands to create the necessary services replacing
<plan> with the appropriate plan name.

**NOTE** If you need to find the plan names, run `cf marketplace`.

```
cf create-service p.service-registry <plan> registry-atm-locator
cf create-service p.gateway <plan> gateway-atm-locator -c '{ "host": "atmlocator" }'
```

* After the services above have been successfully deployed, build and deploy the micro-services to your space by running following commands
from the root of the cloned repository:

```
cd atm-locator-angular
npm install
npm run build
cf push

cd ../atm
./mvnw clean package
cf push

cd ../branch
./mvnw clean package
cf push

cd ../location-translator
./mvnw clean package
cf push

cd ../atm-locator
./mvnw clean package
cf push

cd ../where-for-dinner-availability
./mvnw clean package
cf push
```

* Create routes to direct HTTP traffic through the gateway to the micro-services:

```
cf bind-service atm-locator gateway-atm-locator -c '{ "routes": [ { "path": "/atmsearch", "filters": [ "StripPrefix=0" ] } ]  }'

cf bind-service atm-locator-angular gateway-atm-locator -c '{ "routes": [ { "path": "/**", "order": 1000, "filters": [ "StripPrefix=0" ] } ]  }'
```

**Verify Application Build and Deployment**

Run the following command to verify that applications built and deployed successfully.  

```
cf apps
```

You should see a result similar to the following:

```
Getting apps in org meyerg / space demo as meyerg...

name                            requested state   processes           routes
atm                   started           web:1/1, task:0/0   atm.apps.tas.vmtanzu.com
atm-locator           started           web:1/1, task:0/0   atm-locator.apps.tas.vmtanzu.com
atm-locator-angular   started           web:1/1             atm-locator-angular.apps.tas.vmtanzu.com
branch                started           web:1/1, task:0/0   branch.apps.tas.vmtanzu.com
location-translator   started           web:1/1, task:0/0   location-translator.apps.tas.vmtanzu.com
```


## Testing the Deployment

Assuming the application has successfully deployed, you can test the application navigating to the application's URL with a web browser.  The URL should be:

```
https://atmlocator.<domain>
```

where <domain> will be the primary domain assigned to your Space.