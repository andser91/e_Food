#!/bin/bash
echo Stopping order-service...
docker stop order-service
echo Stopping postgres-order
docker stop postgres-order