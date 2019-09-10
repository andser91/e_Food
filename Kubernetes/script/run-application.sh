#!/bin/bash

echo Starting application...
sudo kubectl create -f order-service/order_service_deployment.yaml
sudo kubectl create -f restaurant-service/restaurant_service_deployment.yaml
sudo kubectl create -f kitchen-service/kitchen_service_deployment.yaml
sudo kubectl create -f consumer-service/consumer_service_deployment.yaml
 
