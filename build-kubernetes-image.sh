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
echo "Building restaurant-service image v1"
docker build -t restaurant-service-kube-v1 -f ./Dockerfile-v1 .
echo "Building restaurant-service image v2"
docker build -t restaurant-service-kube-v2 -f ./Dockerfile-v2 .
cd ../iam
echi "Building iam image"
docker build -t iam-kube .
