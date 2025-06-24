#!/bin/sh

set -e

echo "⌛ Esperando a que DynamoDB esté disponible en http://dynamodb-local:8000..."

# Esperar hasta que Dynamo esté disponible
until curl -s http://dynamodb-local:8000 > /dev/null; do
  sleep 1
done

echo "✅ DynamoDB está disponible. Creando la tabla 'Stats'..."

aws dynamodb create-table \
  --table-name Stats \
  --attribute-definitions AttributeName=timestamp,AttributeType=S \
  --key-schema AttributeName=timestamp,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url http://dynamodb-local:8000 \
  --region us-east-1

echo "✅ Tabla 'Stats' creada correctamente."

