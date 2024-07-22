# Tanzu Application Platform Deployment

The following scenario describes performing running the atm-location application on Tanzu Application Platform.  It includes
configuration for Spring Cloud Services as well pre-generated deployment manifests for the micro-services.  It also includes configuration
for binding the backend micro-services to the Spring Cloud Service instances.

## Installing Spring Cloud Services Packages

Tanzu Application Platform (TAP) includes the carvel packages for Spring Cloud Services, however you are required to `install` the
packages yourself.  Use the following steps to install the packages which will install the Kubernetes controller for each Spring Cloud
service.   **NOTE** The following commands assume you have access to the target Kubernetes cluster and have permission to install
package into the `tap-install` namespace.  You can find the appropriate version number by running `kubectl get package -n tap-install`

```
tanzu package install scc --package config-server.spring.tanzu.vmware.com --version <config-version-number> -n tap-install
tanzu package install scg --package spring-cloud-gateway.tanzu.vmware.com --version <gateway-version-number> -n tap-install
tanzu package install eureka --package service-registry.spring.apps.tanzu.vmware.com --version <eureka-version-number> -n tap-install
```

## Deploy Spring Cloud Service Instances

The `deployment/tap/services` directory contains pre-configured Kubernetes CRs to deploy an instance of each Spring Cloud service along
with an Ingress resource to expose the application outside of the cluster.  You will need update the 
`deployment/tap/services/gatewayServiceInstance.yaml` with the name of your DNS application domain to access the application using
DNS (look for the <MY DOMAIN> placehoder in the file).

To deploy the service instances and configure Spring Cloud Gateway with the necessary route information, 
run the following command from the root of the this directory substituting the <namesapce> placeholder
with an appropriate Kubernetes namespace.

```
kubectl apply -f deployment/tap/services -n <namespace>
```


## Deploy ATM Locator Application

Once the Spring Cloud Service instance are running, you will need to deploy the application's micro-services using the pre-configure
Kubernetes deployment manifest files in the `deployment/tap` directory.  Each backend micro-servce is already configured to activate the Eureka
profile which enables service registry connectivity and discoverability.

To deploy the atm-locator backend and frontend services, run the following command from the root of the this directory substituting the 
<namesapce> placeholder with an appropriate Kubernetes namespace (should be the same namespace as the previous section).

```
kubectl apply -f deployment/tap -n <namespace>
```

## Access The ATM Locator Application

You can access the ATM Locator application via a browser by using the fully qualified domain name that you configured in the 
`deployment/tap/services/gatewayServiceInstance.yaml` file.  Ex: http://atmlocator.perfect300rock.com.

**NOTE:**  The Google Maps API key is not set in the image referenced by the deployment in this repository; the pre-build image will not have the ability
to display maps.
