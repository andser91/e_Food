#!/bin/bash

echo Stopping restaurant-service...

sudo kubectl delete deployment.apps/restaurant-service service/restaurant-service
sudo kubectl delete configmap restaurant-service-config
