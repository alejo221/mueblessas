package co.com.mueblessas.events.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.async.rabbit.config.props.AsyncRabbitPropsDomainProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RabbitMQConnectionConfigTest {
    @InjectMocks
    private RabbitMQConnectionConfig config;

    @Test
    void shouldCreateConnectionProperties(){
        AsyncRabbitPropsDomainProperties rabbitPropsDomainProperties = config.getConnectionProperties("host",1234,"guest","guest");
        assertEquals("guest", rabbitPropsDomainProperties.get("stats").getConnectionProperties().getUsername());
        assertEquals("guest", rabbitPropsDomainProperties.get("stats").getConnectionProperties().getPassword());
        assertEquals(1234, rabbitPropsDomainProperties.get("stats").getConnectionProperties().getPort());
        assertEquals("host", rabbitPropsDomainProperties.get("stats").getConnectionProperties().getHost());
    }
}
