#!/bin/bash

echo Starting the application...

docker-compose build
docker-compose up -d zookeeper kafka
docker-compose up
