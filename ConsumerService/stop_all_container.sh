#!/bin/bash
echo Stopping consumer-service...
docker stop consumer-service
echo Stopping postgres-consumer
docker stop postgres-consumer