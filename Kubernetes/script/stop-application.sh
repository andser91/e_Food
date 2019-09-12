#!/bin/bash

echo Stopping application...

sudo kubectl delete deployment.apps/consumer-service service/consumer-service deployment.apps/kitchen-service service/kitchen-service deployment.apps/order-service service/order-service deployment.apps/restaurant-service-v1 deployment.apps/restaurant-service-v2 service/restaurant-service
sudo kubectl delete configmap consumer-service-config kitchen-service-config order-service-config restaurant-service-config
sudo kubectl delete virtualservice restaurant-service order-service consumer-service kitchen-service
sudo kubectl delete destinationrule restaurant-service
sudo kubectl delete gateway efood-gateway
 
