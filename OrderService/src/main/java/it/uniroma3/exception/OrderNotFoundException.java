package it.uniroma3.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id){
        super("Ordine con id="+id+" non trovato!");
    }
}
