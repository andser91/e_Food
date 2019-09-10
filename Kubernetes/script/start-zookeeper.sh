#!/bin/bash

echo Starting kafka...
sudo kubectl create -f services/zookeeper_deployment.yaml

