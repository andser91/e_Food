#!/bin/bash
echo "Building all project..."
sudo gradle assemble

cd OrderService
echo "Building order-service image"
docker build -t order-service-kube .
cd ../KitchenService
echo "Building kitchen-service image"
docker build -t kitchen-service-kube .
cd ../ConsumerService
echo "Building consumer-service image"
docker build -t consumer-service-kube .
cd ../RestaurantService
echo "Building restaurant-service image"
docker build -t restaurant-service-kube .
