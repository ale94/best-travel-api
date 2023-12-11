package com.alejandro.best_travel.domain.repositories;

import com.alejandro.best_travel.domain.entities.TicketEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {

}
