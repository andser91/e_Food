#!/bin/bash

sudo kubectl create namespace istio-system
sudo kubectl apply -f ./cluster/kiali-secret.yaml
sudo helm template istio-1.2.5/install/kubernetes/helm/istio-init --name istio-init --namespace istio-system | kubectl apply -f -
sudo helm template istio-1.2.5/install/kubernetes/helm/istio --name istio --namespace istio-system --set grafana.enabled=true --set kiali.enabled=true --set prometheus.enabled=true --set tracing.enabled=true | kubectl apply -f -
sudo kubectl label namespace default istio-injection=enabled --overwrite

