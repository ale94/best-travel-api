package ar.com.alejandro.best_travel_api.domain.repositories;

import ar.com.alejandro.best_travel_api.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
