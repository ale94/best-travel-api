package ar.com.alejandro.best_travel_api.domain.repositories;

import ar.com.alejandro.best_travel_api.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<TourEntity, Long> {
}
