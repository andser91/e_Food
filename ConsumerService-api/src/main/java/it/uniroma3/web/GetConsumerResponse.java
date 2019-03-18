package it.uniroma3.web;

public class GetConsumerResponse {

    private Long consumerId;
    private String firstName;
    private String lastName;

    public GetConsumerResponse() {
    }

    public GetConsumerResponse(Long consumerId, String firstName, String lastName) {
        this.consumerId = consumerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
