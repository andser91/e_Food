#!/bin/bash

echo Starting application...
sudo kubectl apply -f  cluster/bug_fix_istio-mysql.yaml
sudo kubectl create -f order-service/order_service_deployment.yaml
sudo kubectl create -f restaurant-service/restaurant_service_deployment.yaml
sudo kubectl create -f kitchen-service/kitchen_service_deployment.yaml
sudo kubectl create -f consumer-service/consumer_service_deployment.yaml
sudo kubectl create -f cluster/ingress.yaml