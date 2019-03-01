#!/bin/bash

# Script per ottenere le immagini base dei contenitori

echo Getting docker images...
docker pull postgres:latest
docker pull openjdk:8
echo Build order-img...
docker build -t order-img .






