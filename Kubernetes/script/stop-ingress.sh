#!/bin/bash

echo Stopping ngnix ingress...
sudo kubectl delete ingress efood-ingress
