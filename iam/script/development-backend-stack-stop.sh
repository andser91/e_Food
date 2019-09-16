#!/bin/bash
echo "Stopping development backend containers"
docker stop environments_mysql_1
echo "Removing development backend containers"
docker rm environments_mysql_1

