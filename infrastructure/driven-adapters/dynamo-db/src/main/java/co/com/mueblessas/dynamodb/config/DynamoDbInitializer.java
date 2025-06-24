package co.com.mueblessas.dynamodb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.concurrent.CompletableFuture;

@Component
public class DynamoDbInitializer  {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDbInitializer.class);

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public DynamoDbInitializer(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createTableIfNotExists() {
        String tableName = "Stats";

        dynamoDbAsyncClient.describeTable(DescribeTableRequest.builder()
                        .tableName(tableName)
                        .build())
                .handle((response, throwable) -> {
                    if (throwable != null) {
                        if (throwable.getCause() instanceof ResourceNotFoundException
                                || throwable instanceof ResourceNotFoundException) {
                            logger.info("Tabla '{}' no encontrada, creando...", tableName);
                            return createTable(tableName);
                        } else {
                            logger.error("Error al describir tabla '{}': {}", tableName, throwable.getMessage(), throwable);
                            return CompletableFuture.failedFuture(throwable);
                        }
                    } else {
                        logger.info("La tabla '{}' ya existe.", tableName);
                        return CompletableFuture.completedFuture(response);
                    }
                })
                .thenCompose(cf -> cf) // aplanamos el CompletableFuture<CompletableFuture<?>>
                .whenComplete((resp, err) -> {
                    if (err != null) {
                        logger.error("Error al crear/verificar la tabla '{}': {}", tableName, err.getMessage(), err);
                    } else {
                        logger.info("Operación sobre tabla '{}' completada exitosamente.", tableName);
                    }
                });
    }

    private CompletableFuture<CreateTableResponse> createTable(String tableName) {
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName(tableName)
                .keySchema(KeySchemaElement.builder()
                        .attributeName("timestamp")
                        .keyType(KeyType.HASH)
                        .build())
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName("timestamp")
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build();

        return dynamoDbAsyncClient.createTable(request)
                .thenApply(response -> {
                    logger.info("Tabla '{}' creada exitosamente.", tableName);
                    return response;
                });
    }
}