#!/bin/bash

echo Starting all services...
sudo kubectl create -f services/mysql_deployment.yaml
sudo kubectl create -f services/zookeeper_deployment.yaml
sudo kubectl create -f services/kafka_deployment.yaml 

