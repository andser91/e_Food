#!/bin/bash

echo Stopping kitchen-service...

sudo kubectl delete deployment.apps/kitchen-service service/kitchen-service
sudo kubectl delete configmap kitchen-service-config
