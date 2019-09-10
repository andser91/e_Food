#!/bin/bash

echo Starting ngnix ingress...
sudo kubectl create -f cluster/ingress.yaml
