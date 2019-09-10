#!/bin/bash

echo Starting kitchen-service...
sudo kubectl create -f kitchen-service/kitchen_service_deployment.yaml 
