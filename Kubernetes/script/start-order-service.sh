#!/bin/bash

echo Stopping order-service...
sudo kubectl create -f order-service/order_service_deployment.yaml 
