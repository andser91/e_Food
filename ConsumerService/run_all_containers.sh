#!/bin/bash

echo Starting postgres-consumer container...
docker run --name postgres-consumer -e POSTGRES_PASSWORD=postgres \
			-e POSTGRES_DB=test_consumers -e POSTGRES_USER=postgres \
			-d postgres:latest

echo Starting consumer-service
docker run -p 8082:8082 --name=consumer-service \
		   --link postgres-consumer:postgres consumer-img