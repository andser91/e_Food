#!/bin/bash

echo Starting the client

docker build -t e_food_rest-client ./RestClient/
docker run e_food_rest-client


