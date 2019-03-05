package it.uniroma3.adapter;

import it.uniroma3.domain.ConsumerServiceAdapter;
import org.springframework.beans.factory.annotation.Value;

public class ConsumerServiceAdapterRest implements ConsumerServiceAdapter {

    @Value("${efood.consumerservice.uri}")
    private String consumerServiceUri;

    @Override
    public boolean validateConsumer(Long consumerId) {
        String consumerUrl = consumerServiceUri + "/consumers/{consumerId}";

        return false;
    }
}
