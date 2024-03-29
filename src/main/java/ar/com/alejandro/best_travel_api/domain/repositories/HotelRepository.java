package ar.com.alejandro.best_travel_api.domain.repositories;

import ar.com.alejandro.best_travel_api.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    List<HotelEntity> findByPriceLessThan(BigDecimal price);

    List<HotelEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<HotelEntity> findByRatingGreaterThan(Integer rating);

    Optional<HotelEntity> findByReservationsId(UUID id);
}
