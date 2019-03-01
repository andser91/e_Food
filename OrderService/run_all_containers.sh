#!/bin/bash

echo Starting postgres-order container...
docker run --name postgres-order -e POSTGRES_PASSWORD=postgres \
			-e POSTGRES_DB=test_orders -e POSTGRES_USER=postgres \
			-d postgres:latest

echo Starting order-service
docker run -p 8083:8083 --name=order-service \
		   --link postgres-order:postgres order-img