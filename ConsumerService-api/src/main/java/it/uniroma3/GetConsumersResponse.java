package it.uniroma3;

import java.util.List;

public class GetConsumersResponse {
    private List<GetConsumerResponse> consumers;


    public GetConsumersResponse() {
    }

    public GetConsumersResponse(List<GetConsumerResponse> consumers) {
        this.consumers = consumers;
    }

    public List<GetConsumerResponse> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<GetConsumerResponse> consumers) {
        this.consumers = consumers;
    }
}
