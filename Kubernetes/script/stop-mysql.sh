#!/bin/bash

echo Starting mysql...
sudo kubectl delete statefulset.apps/mysql service/mysql
sudo kubectl delete configmap mysql-config mysql-initdb-config

