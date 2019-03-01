#!/bin/bash

# Script per ottenere le immagini base dei contenitori

echo Getting docker images...
docker pull postgres:latest
docker pull openjdk:8
echo Build restaurant-img...
docker build -t restaurant-img .






