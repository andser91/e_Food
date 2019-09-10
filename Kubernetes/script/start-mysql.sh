#!/bin/bash

echo Starting mysql...
sudo kubectl create -f services/mysql_deployment.yaml

