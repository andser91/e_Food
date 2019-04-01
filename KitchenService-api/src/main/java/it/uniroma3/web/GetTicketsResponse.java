package it.uniroma3.web;

import java.util.List;

public class GetTicketsResponse {

    private List<GetTicketResponse> responses;

    public GetTicketsResponse(){}

    public GetTicketsResponse(List<GetTicketResponse> responses) {
        this.responses = responses;
    }

    public List<GetTicketResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<GetTicketResponse> responses) {
        this.responses = responses;
    }
}
