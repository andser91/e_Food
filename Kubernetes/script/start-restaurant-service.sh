#!/bin/bash

echo Stopping restaurant-service...
sudo kubectl create -f restaurant-service/restaurant_service_deployment.yaml 
