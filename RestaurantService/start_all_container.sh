#!/bin bash

echo Starting postgres-restaurant
docker start postgres-restaurant
echo Starting restaurant-service...
docker start restaurant-service