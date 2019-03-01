#!/bin/bash
echo Removing containers...
./stop_all_container.sh
docker rm postgres-order
docker rm order-service