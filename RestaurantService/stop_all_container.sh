#!/bin/bash
echo Stopping restaurant-service...
docker stop restaurant-service
echo Stopping postgres-restaurant
docker stop postgres-restaurant