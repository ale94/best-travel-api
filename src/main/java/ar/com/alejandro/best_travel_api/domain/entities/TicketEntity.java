package ar.com.alejandro.best_travel_api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "ticket")
public class TicketEntity implements Serializable {

    @Id
    private UUID id;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
}
