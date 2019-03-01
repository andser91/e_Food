#!/bin/bash
echo Removing containers...
./stop_all_container.sh
docker rm postgres-restaurant
docker rm restaurant-service