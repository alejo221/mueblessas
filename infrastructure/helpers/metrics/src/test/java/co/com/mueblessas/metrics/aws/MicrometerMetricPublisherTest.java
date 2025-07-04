package co.com.mueblessas.metrics.aws;

import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.metrics.internal.EmptyMetricCollection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MicrometerMetricPublisherTest {

    @Test
    void metricTest() {
        LoggingMeterRegistry loggingMeterRegistry = LoggingMeterRegistry
            .builder(LoggingRegistryConfig.DEFAULT)
            .build();

        MicrometerMetricPublisher micrometerMetricPublisher = new MicrometerMetricPublisher(loggingMeterRegistry);

        micrometerMetricPublisher.publish(EmptyMetricCollection.create());
        micrometerMetricPublisher.close();

        assertNotNull(micrometerMetricPublisher);

    }
}