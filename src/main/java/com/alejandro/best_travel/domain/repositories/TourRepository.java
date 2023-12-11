package com.alejandro.best_travel.domain.repositories;

import com.alejandro.best_travel.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<TourEntity, Long> {

}
