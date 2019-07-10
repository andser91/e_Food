package it.uniroma3.domain;

import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority,Long> {
    Authority findByName(AuthorityName name);
}
