#!/bin bash

echo Starting postgres-consumer
docker start postgres-consumer
echo Starting consumer-service...
docker start consumer-service