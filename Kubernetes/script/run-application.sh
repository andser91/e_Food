#!/bin/bash

echo Starting application...
sudo kubectl apply -f  cluster/bug_fix_istio-mysql.yaml
sudo kubectl apply -f iam/iam_deployment.yaml
sudo kubectl apply -f order-service/order_service_deployment.yaml
sudo kubectl apply -f restaurant-service/restaurant_service_deployment.yaml
sudo kubectl apply -f kitchen-service/kitchen_service_deployment.yaml
sudo kubectl apply -f consumer-service/consumer_service_deployment.yaml
sudo kubectl apply -f cluster/ingress.yaml