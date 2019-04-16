package it.uniroma3.domain;

import io.eventuate.javaclient.spring.jdbc.IdGenerator;
import io.eventuate.javaclient.spring.jdbc.IdGeneratorImpl;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.jdbc.CommonJdbcMessagingConfiguration;
import io.eventuate.tram.messaging.producer.MessageProducer;
import io.eventuate.tram.messaging.producer.jdbc.MessageProducerJdbcImpl;
import io.eventuate.tram.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import io.eventuate.tram.sagas.orchestration.*;

import io.eventuate.tram.sagas.participant.SagaLockManager;
import io.eventuate.tram.sagas.participant.SagaLockManagerImpl;
import it.uniroma3.sagas.CreateOrderSaga;
import it.uniroma3.sagas.CreateOrderSagaState;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;


@Configuration
//@Import({TramEventsPublisherConfiguration.class,  SagaOrchestratorConfiguration.class, TramMessageProducerJdbcConfiguration.class,  CommonJdbcMessagingConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
public class OrderServiceConfiguration {
    //

    @Bean
    public SagaManager<CreateOrderSagaState> createOrderSagaManager(CreateOrderSaga saga) {
        return new SagaManagerImpl<>(saga);
    }

    @Bean
    public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
        return registry -> registry.config().commonTags("service", serviceName);
    }

}
//    @Bean
//    public DataSource dataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("eventuate-tram-embedded-schema.sql")
//                .addScript("eventuate-tram-sagas-embedded.sql")
//                .build();
//    }
//
//
//    @Bean
//    public SagaCommandProducer sagaCommandProducer() {
//        return new SagaCommandProducer();
//    }*/
//    /*
//    @Bean
//    public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService, ConsumerServiceProxy consumerService, KitchenServiceProxy kitchenServiceProxy) {
//        return new CreateOrderSaga(orderService, consumerService, kitchenServiceProxy);
//    }
//
//
//    @Bean
//    public KitchenServiceProxy kitchenServiceProxy() {
//        return new KitchenServiceProxy();
//    }
//
//    @Bean
//    public OrderServiceProxy orderServiceProxy() {
//        return new OrderServiceProxy();
//    }
//
//    @Bean
//    public ConsumerServiceProxy consumerServiceProxy() {
//        return new ConsumerServiceProxy();
//    }
//
//    @Bean
//    public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
//        return registry -> registry.config().commonTags("service", serviceName);
//    }
//    @Bean
//    public MeterRegistryCustomizer meterRegistryCustomizer(@Value("${spring.application.name}") String serviceName) {
//        return registry -> registry.config().commonTags("service", serviceName);
//    }
//}
