package it.uniroma3;

public class CreateConsumerResponse {

    private Long consumerId;

    public CreateConsumerResponse() {
    }

    public CreateConsumerResponse(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }
}
