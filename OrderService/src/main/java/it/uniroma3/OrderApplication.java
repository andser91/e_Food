package it.uniroma3;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import io.eventuate.tram.sagas.orchestration.SagaManagerImpl;
import io.eventuate.tram.sagas.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;
import it.uniroma3.domain.OrderServiceConfiguration;
import it.uniroma3.sagas.CreateOrderSaga;
import it.uniroma3.sagas.CreateOrderSagaState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Configuration
@Import({CommonSwaggerConfig.class, OrderServiceConfiguration.class, TramJdbcKafkaConfiguration.class, TramEventsPublisherConfiguration.class,         TramCommandProducerConfiguration.class,
        SagaOrchestratorConfiguration.class, TramCommandProducerConfiguration.class, SagaParticipantConfiguration.class
})
public class OrderApplication {
    @Bean
    public ChannelMapping channelMapping() {
        return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
    }
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
