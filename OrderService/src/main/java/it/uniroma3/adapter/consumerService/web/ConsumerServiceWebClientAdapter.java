package it.uniroma3.adapter.consumerService.web;

import it.uniroma3.web.GetConsumerResponse;
import it.uniroma3.domain.ConsumerServiceAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Service
@Primary
public class ConsumerServiceWebClientAdapter implements ConsumerServiceAdapter {

    @Value("${efood.consumerservice.uri}")
    private String consumerServiceUri;

    private WebClient webClient;

    public ConsumerServiceWebClientAdapter(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(consumerServiceUri).build();
    }


    @Override
    public boolean validateConsumer(Long consumerId) {
        String consumerUrl = consumerServiceUri + "/consumers/{consumerId}";
        GetConsumerResponse consumer = null;
        Mono<GetConsumerResponse> response = webClient
                .get()
                .uri(consumerUrl, consumerId)
                .retrieve()
                .bodyToMono(GetConsumerResponse.class);
        try {
            consumer = response.block();

        } catch (WebClientException e) {
            //logging
        }
        return consumer!=null;
    }
}
