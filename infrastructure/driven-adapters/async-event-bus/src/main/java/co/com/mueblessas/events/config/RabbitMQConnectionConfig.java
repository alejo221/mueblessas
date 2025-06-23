package co.com.mueblessas.events.config;

import lombok.RequiredArgsConstructor;

import org.reactivecommons.async.rabbit.config.RabbitProperties;
import org.reactivecommons.async.rabbit.config.props.AsyncProps;
import org.reactivecommons.async.rabbit.config.props.AsyncRabbitPropsDomainProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConnectionConfig {

    @Bean
    @Primary
    @Profile("local")
    public AsyncRabbitPropsDomainProperties getConnectionProperties(@Value("${spring.rabbitmq.host}") String host,
                                                                    @Value("${spring.rabbitmq.port}") Integer port,
                                                                    @Value("${spring.rabbitmq.username}") String username,
                                                                    @Value("${spring.rabbitmq.password}") String password){
        RabbitProperties properties = new RabbitProperties();
        properties.setHost(host);
        properties.setPort(port);
        properties.setUsername(username);
        properties.setPassword(password);

        return AsyncRabbitPropsDomainProperties.builder().withDomain("stats", AsyncProps.
                builder()
                .connectionProperties(properties)
                .build())
                .build();
    }
}
