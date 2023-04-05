#!/bin/bash

CONTAINER_ID=$(docker ps -f  "name=server-springboot" -q)

cd /home/ubuntu/gloddy_server
if [ -n ${CONTAINER_ID} ]; then
  docker-compose down
fi

echo "application start"
docker-compose up -d