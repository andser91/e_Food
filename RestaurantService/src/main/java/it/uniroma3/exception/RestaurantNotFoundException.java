package it.uniroma3.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(Long id){
        super("Ristorante con id="+id+" non trovato!");
    }
}
