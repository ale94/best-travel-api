package com.alejandro.best_travel.domain.repositories;

import com.alejandro.best_travel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlyRepository extends JpaRepository<FlyEntity, Long> {

}
