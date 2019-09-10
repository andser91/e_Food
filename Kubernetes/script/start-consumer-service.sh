#!/bin/bash

echo Starting consumer-service...
sudo kubectl create -f consumer-service/consumer_service_deployment.yaml 
