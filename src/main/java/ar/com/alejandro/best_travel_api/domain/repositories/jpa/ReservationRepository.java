package ar.com.alejandro.best_travel_api.domain.repositories.jpa;

import ar.com.alejandro.best_travel_api.domain.entities.jpa.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
