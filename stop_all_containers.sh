#!/bin/bash
echo Stopping restaurant-service...
docker stop efood_restaurant-service_1
echo Stopping postgres-service
docker stop efood_postgres-service_1
echo Stopping consumer-service
docker stop efood_consumer-service_1
echo Stopping order-service
docker stop efood_order-service_1