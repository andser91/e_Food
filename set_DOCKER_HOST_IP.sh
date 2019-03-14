#!/bin/bash

if [ -z "$DOCKER_HOST_IP" ] ; then
    echo "please source this file" 
    export DOCKER_HOST_IP=10.11.1.188
fi

echo DOCKER_HOST_IP is $DOCKER_HOST_IP 
