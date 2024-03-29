package ar.com.alejandro.best_travel_api.domain.repositories;

import ar.com.alejandro.best_travel_api.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {
}
