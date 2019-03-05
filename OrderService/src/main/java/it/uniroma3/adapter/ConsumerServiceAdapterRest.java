package it.uniroma3.adapter;

import it.uniroma3.GetConsumerResponse;
import it.uniroma3.domain.ConsumerServiceAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ConsumerServiceAdapterRest implements ConsumerServiceAdapter {

    @Value("${efood.consumerservice.uri}")
    private String consumerServiceUri;

    @Override
    public boolean validateConsumer(Long consumerId) {
        String consumerUrl = consumerServiceUri + "/consumers/{consumerId}";
        GetConsumerResponse consumerResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<GetConsumerResponse> entity = restTemplate.getForEntity(consumerUrl, GetConsumerResponse.class, consumerId);
            consumerResponse = entity.getBody();
        } catch (RestClientException e) {
            //logger
        }
        return consumerResponse != null;
    }
}
