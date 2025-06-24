#!/bin/sh
set -e


until curl -s http://dynamodb-local:8000 > /dev/null; do
  sleep 1
done


aws dynamodb create-table \
  --table-name Stats \
  --attribute-definitions AttributeName=timestamp,AttributeType=S \
  --key-schema AttributeName=timestamp,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url http://dynamodb-local:8000 \
  --region us-west-2


