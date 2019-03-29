package it.uniroma3.domain;

import org.springframework.data.repository.CrudRepository;

public interface KitchenRepository extends CrudRepository<Ticket, Long> {
}
