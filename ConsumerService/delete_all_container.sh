#!/bin/bash
echo Removing containers...
./stop_all_container.sh
docker rm postgres-consumer
docker rm consumer-service