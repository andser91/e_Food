package it.uniroma3.web;

public class GetRestaurantResponse {
    private Long id;
    private String address;
    private String name;

    public GetRestaurantResponse() {
    }

    public GetRestaurantResponse(Long id, String address, String name) {
        this.id = id;
        this.address = address;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
