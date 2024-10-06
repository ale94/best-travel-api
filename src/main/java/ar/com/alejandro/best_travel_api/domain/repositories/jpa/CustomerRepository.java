package ar.com.alejandro.best_travel_api.domain.repositories.jpa;

import ar.com.alejandro.best_travel_api.domain.entities.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
