#!/usr/bin/env bash

IMAGE=backend-bci
CONTAINER=api-bci-container
PORT=80

docker build -t $IMAGE .

if docker container ls | grep $CONTAINER > /dev/null; then
	docker container stop $CONTAINER
fi

if docker container ls -a | grep $CONTAINER > /dev/null; then
  docker container rm $CONTAINER
fi

docker-compose up -d