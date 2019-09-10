#!/bin/bash

echo Stopping consumer-service...

sudo kubectl delete deployment.apps/consumer-service service/consumer-service
sudo kubectl delete configmap consumer-service-config
