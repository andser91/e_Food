#!/bin/bash

echo Stopping application...

sudo kubectl delete deployment.apps/iam deployment.apps/consumer-service service/consumer-service deployment.apps/kitchen-service service/kitchen-service deployment.apps/order-service service/order-service deployment.apps/restaurant-service-v1 deployment.apps/restaurant-service-v2 service/restaurant-service service/iam
sudo kubectl delete configmap consumer-service-config kitchen-service-config order-service-config restaurant-service-config iam-config
sudo kubectl delete virtualservice restaurant-service order-service consumer-service kitchen-service iam
sudo kubectl delete destinationrule restaurant-service
sudo kubectl delete gateway efood-gateway
 
