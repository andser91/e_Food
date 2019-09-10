#!/bin/bash

echo Stopping kafka...

sudo kubectl delete statefulset.apps/kafka-broker service/kafka-broker
sudo kubectl delete configmap kafka-config 
