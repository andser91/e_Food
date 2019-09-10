#!/bin/bash

echo Stopping application...

sudo kubectl delete deployment.apps/consumer-service service/consumer-service
sudo kubectl delete configmap consumer-service-config
sudo kubectl delete deployment.apps/kitchen-service service/kitchen-service
sudo kubectl delete configmap kitchen-service-config
sudo kubectl delete deployment.apps/order-service service/order-service
sudo kubectl delete configmap order-service-config
sudo kubectl delete deployment.apps/restaurant-service service/restaurant-service
sudo kubectl delete configmap restaurant-service-config
 
