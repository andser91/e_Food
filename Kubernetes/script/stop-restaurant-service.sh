#!/bin/bash

echo Stopping restaurant-service...

sudo kubectl delete deployment.apps/restaurant-service-v1 deployment.apps/restaurant-service-v2 service/restaurant-service
sudo kubectl delete configmap restaurant-service-config
