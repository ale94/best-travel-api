package ar.com.alejandro.best_travel_api.infraestructure.helpers;

import ar.com.alejandro.best_travel_api.domain.entities.jpa.*;
import ar.com.alejandro.best_travel_api.domain.repositories.jpa.ReservationRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.jpa.TicketRepository;
import ar.com.alejandro.best_travel_api.infraestructure.services.ReservationService;
import ar.com.alejandro.best_travel_api.infraestructure.services.TicketService;
import ar.com.alejandro.best_travel_api.util.BestTravelUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer) {
        Set<TicketEntity> response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(fly -> {
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGES_PRICE_PERCENTAGE)))
                    .purchaseDate(LocalDateTime.now())
                    .arrivalDate(BestTravelUtil.getRandomLatter())
                    .departureDate(BestTravelUtil.getRandomSoon())
                    .build();
            response.add(ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer) {
        Set<ReservationEntity> response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            var reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)))
                    .hotel(hotel)
                    .customer(customer)
                    .totalDays(totalDays)
                    .build();
            response.add(reservationRepository.save(reservationToPersist));
        });
        return response;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer) {
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGES_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDateTime.now())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .departureDate(BestTravelUtil.getRandomSoon())
                .build();
        return this.ticketRepository.save(ticketToPersist);
    }

    public ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays) {
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)))
                .hotel(hotel)
                .customer(customer)
                .totalDays(totalDays)
                .build();
        return this.reservationRepository.save(reservationToPersist);
    }

}
