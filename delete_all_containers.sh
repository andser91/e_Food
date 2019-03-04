#!/bin/bash
echo Removing containers...
docker rm efood_postgres-service_1
docker rm efood_restaurant-service_1
docker rm efood_consumer-service_1
docker rm efood_order-service_1