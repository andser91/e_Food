package it.uniroma3.common.endpoint.event;


import it.uniroma3.common.event.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonEventConfiguration {

    @Bean
    public DomainEventPublisher domainEventPublisher() {
        return new DomainEventPublisherImpl();
    }
}
