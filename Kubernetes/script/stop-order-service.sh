#!/bin/bash

echo Stopping order-service...

sudo kubectl delete deployment.apps/order-service service/order-service
sudo kubectl delete configmap order-service-config
