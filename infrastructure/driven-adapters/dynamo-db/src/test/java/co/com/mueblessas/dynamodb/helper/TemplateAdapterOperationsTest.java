package co.com.mueblessas.dynamodb.helper;

import co.com.mueblessas.dynamodb.StatsEntity;
import co.com.mueblessas.dynamodb.StatsTemplateAdapter;
import co.com.mueblessas.model.stats.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<StatsEntity> customerTable;

    private StatsEntity modelEntity;

    private StatsTemplateAdapter statsTemplateAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("Stats", TableSchema.fromBean(StatsEntity.class)))
                .thenReturn(customerTable);

        modelEntity = new StatsEntity();

    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        StatsEntity modelEntityUnderTest = new StatsEntity(anyString(),anyInt(),anyInt(),
                anyInt(),anyInt(),anyInt(),anyInt(),anyInt(),anyString());

        assertNotNull(modelEntityUnderTest.getTimestamp());
        assertNotNull(modelEntityUnderTest.getHash());
    }

//    @Test
//    void testSave() {
//        when(customerTable.putItem(modelEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
//        when(mapper.map(modelEntity, StatsEntity.class)).thenReturn(modelEntity);
//
//        statsTemplateAdapter =
//                new StatsTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper, "Stats");
//
//        StepVerifier.create(statsTemplateAdapter.save(modelEntity))
//                .expectNextCount(Stats.builder().build())
//                .verifyComplete();
//    }
//
//    @Test
//    void testGetById() {
//        String id = "id";
//
//        when(customerTable.getItem(
//                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
//                .thenReturn(CompletableFuture.completedFuture(modelEntity));
//        when(mapper.map(modelEntity, Object.class)).thenReturn("value");
//
//        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
//                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper,"Stats");
//
//        StepVerifier.create(dynamoDBTemplateAdapter.getById("hash"))
//                .expectNext()
//                .verifyComplete();
//    }
//
//    @Test
//    void testDelete() {
//        when(mapper.map(modelEntity, StatsEntity.class)).thenReturn(modelEntity);
//        when(mapper.map(modelEntity, Object.class)).thenReturn("value");
//
//        when(customerTable.deleteItem(modelEntity))
//                .thenReturn(CompletableFuture.completedFuture(modelEntity));
//
//        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
//                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper,"Stats");
//
//        StepVerifier.create(dynamoDBTemplateAdapter.delete(modelEntity))
//                .expectNext("value")
//                .verifyComplete();
//    }
}