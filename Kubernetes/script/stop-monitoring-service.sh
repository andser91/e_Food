#!/bin/bash

echo "Stopping monitoring service"

sudo kubectl -n istio-system delete virtualservice grafana-vs kiali-vs prometheus-vs tracing-vs
sudo kubectl -n istio-system delete gateway grafana-gateway kiali-gateway prometheus-gateway tracing-gateway
sudo kubectl -n istio-system delete destinationrule grafana kiali prometheus tracing

