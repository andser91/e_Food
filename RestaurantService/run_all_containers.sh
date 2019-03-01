#!/bin/bash

echo Starting postgres-restaurant container...
docker run --name postgres-restaurant -e POSTGRES_PASSWORD=postgres \
			-e POSTGRES_DB=test_restaurants -e POSTGRES_USER=postgres \
			-d postgres:latest

echo Starting restaurant-service
docker run -p 8081:8081 --name=restaurant-service \
		   --link postgres-restaurant:postgres restaurant-img