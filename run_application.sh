#!/bin/bash


if [ -z "$DOCKER_HOST_IP" ] ; then
    echo "please do source set-docker-host-ip.sh"
    export DOCKER_HOST_IP=10.11.1.188
fi

echo Starting the application...

docker-compose build
docker-compose up -d zookeeper kafka
docker-compose up
