#!/bin/bash
echo "########### Setup Docker ###########"
docker-compose -f aws-dynamodb-local-setup-tutorial_aws-dynamo-db-using-docker-image_docker-compose.yaml up -d
echo "########### Docker is setup an dscripts are done running #########"