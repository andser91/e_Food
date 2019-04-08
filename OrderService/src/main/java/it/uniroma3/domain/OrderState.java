package it.uniroma3.domain;

public enum OrderState {
    PENDING,
    CONSUMER_APPROVED,
    RESTAURANT_APPROVED,
    KITCHEN_APPROVED,
    APPROVED,
    INVALID,
}