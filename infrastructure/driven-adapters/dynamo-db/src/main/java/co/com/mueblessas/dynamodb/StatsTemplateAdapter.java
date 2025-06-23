package co.com.mueblessas.dynamodb;

import co.com.mueblessas.dynamodb.helper.TemplateAdapterOperations;
import co.com.mueblessas.model.stats.Stats;
import co.com.mueblessas.model.stats.gateways.StatsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;


@Repository
public class StatsTemplateAdapter extends TemplateAdapterOperations<Stats, String, StatsEntity >  implements StatsRepository {

    public StatsTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper,
                                @Value("${aws.dynamodb.table-name}") String tableName) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, Stats.class), tableName );
    }

//    public Mono<List<Stats>> getEntityBySomeKeys(String partitionKey, String sortKey) {
//        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
//        return query(queryExpression);
//    }
//
//    public Mono<List<Stats>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
//        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
//        return queryByIndex(queryExpression, "secondary_index" /*index is optional if you define in constructor*/);
//    }
//
//    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
//        return QueryEnhancedRequest.builder()
//                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
//                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
//                .build();
//    }
}
