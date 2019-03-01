#!/bin bash

echo Starting postgres-order
docker start postgres-order
echo Starting order-service...
docker start order-service