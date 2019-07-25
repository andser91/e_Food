#!/bin/bash

echo Starting the client

docker build -t e_food_rest-client ./RestClient/
docker run --network=e_food_default e_food_rest-client


