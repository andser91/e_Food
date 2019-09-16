#!/bin/bash
echo "Starting development backend container"
docker-compose -f ../environments/development-backend-stack.yml up -d