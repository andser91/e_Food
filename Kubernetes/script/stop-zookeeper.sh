#!/bin/bash

echo Starting kafka...
sudo kubectl delete statefulset.apps/zookeeper service/zookeeper

