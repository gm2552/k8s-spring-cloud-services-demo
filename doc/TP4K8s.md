# Tanzu Platform For Kubernetes

The following scenario describes performing running the atm-location application on Tanzu Platform For Kubernetes.  It includes prebuilt
`ContainerApps` as well as Tanzu CLI commands for creating Spring Cloud Service instances and binding them to the `ContainerApps`

This scenario assumes a developer `Space` has already been created with profiles that include Spring Cloud Services capabilities.  Iopert also
assumes that you have configured the Tanzu CLI to `use` the precrated `Space`.

## Deploy Spring Cloud Service Instances

The TP4Ks `Spring Profile` already includes creating an instance of Spring Cloud Gateway, so you will not need to explicitly create a
SCG instance.

To create instances of Eureka and Spring Cloud Config, following the steps below:

Validate that the Eureka and Spring Cloud Config services exist in your space by running the following command:

```
tanzu service type list
```

The output should look similar to the following:

```
  TYPE                   BINDING TYPE    
  PreProvisionedService  <configurable>  
  EurekaServer           eureka          
  ConfigServer           config          
  RedisCluster           redis           
  MongoDBInstance        mongodb         
  MySQLInstance          mysql           
  PostgreSQLInstance     postgresql      
  KafkaInstance          kafka           
  RabbitmqCluster        rabbitmq  
```


Create instances of Eureka and Spring Cloud Config by running the following commands:


```
tanzu service create EurekaServer/atm-locator-eureka
tanzu service create ConfigServer/atm-locator-config --parameter backends='[{git: {uri: "https://github.com/gm2552/atm-locator-config"}}]'
```

Validate that the service instances have been created by running the following commnad:

```
tanzu service list
```

The output should look similar to the following:

```
  NAME                             CONNECTOR  BINDING TYPE  BOUND TO  
  ConfigServer/atm-locator-config  main       config        0 apps    
  EurekaServer/atm-locator-eureka  main       eureka        0 apps   
```


## Deploy ATM Locator Application

This repository includes prebuild configuration for each micro-service, but you can also build each micro-service yourself.

To deploy the microservices using the prebuilt configuration, run the following command from the root directory of this repository:
**NOTE:**  The Google Maps API key is not set in the image referenced by the deployment in this repository; the pre-build image will not have the ability
to display maps.  Use the `tanzu deploy` method in the next paragraph to set your own API key.

```
tanzu deploy --from-build prebuilt
```

To create a fresh build (which will use your own image registry) and deploy the application, run the following command
from the root directory of this repository.  **NOTE:**  The source code has a placehoder for the Google Maps API Key; you 
will need to update the `MY_GOOGLE_MAPS_API_KEY` planholder in the `atm-locator-angular/src/environment/environment.prod.ts` file with
your own API key in order for the mapping feature to work.

```
tanzu deploy
```

Both commands above will also deploy Spring Cloud Gateway routing information.

Validate that the micro-services were successfully deployed by running the following command:

```
tanzu apps list
```

The output should look similar to the following:

```
meyerg@C02GJ2RSMD6R k8s-spring-cloud-services-demo % tanzu apps list
  NAME                 CONTENT                             REPLICAS(RUNNING/REQUESTED)  CPU   MEM  BINDINGS  STATUS   
  branch               git:99d5472 @ 2024-07-15T12:35:47Z  1/(not requested)*           300m  1Gi            Running  
  atm-locator-angular  git:99d5472 @ 2024-07-15T12:35:47Z  1/(not requested)*           300m  1Gi            Running  
  atm-locator          git:99d5472 @ 2024-07-15T12:35:47Z  1/(not requested)*           300m  1Gi            Running  
  atm                  git:99d5472 @ 2024-07-15T12:35:47Z  1/(not requested)*           300m  1Gi            Running  
  location-translator  git:99d5472 @ 2024-07-15T12:35:47Z  1/(not requested)*           300m  1Gi            Running  
```

## Bind Micro-services to Spring Cloud Services Instances

In order for the micro-services to connect and use the Spring Cloud Services, a service binding needs to be made.  Run the following commands to bind
the micro-services to the Spring Cloud Services from the root directory of this repository:


```
tanzu service bind 
```