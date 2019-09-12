#!/bin/bash

echo "Starting monitoring service"

sudo kubectl apply -f monitoring/grafana.yaml
sudo kubectl apply -f monitoring/kiali.yaml
sudo kubectl apply -f monitoring/prometheus.yaml
sudo kubectl apply -f monitoring/tracing.yaml
