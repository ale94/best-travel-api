package com.alejandro.best_travel;

import com.alejandro.best_travel.domain.entities.ReservationEntity;
import com.alejandro.best_travel.domain.entities.TicketEntity;
import com.alejandro.best_travel.domain.entities.TourEntity;
import com.alejandro.best_travel.domain.repositories.CustomerRepository;
import com.alejandro.best_travel.domain.repositories.FlyRepository;
import com.alejandro.best_travel.domain.repositories.HotelRepository;
import com.alejandro.best_travel.domain.repositories.ReservationRepository;
import com.alejandro.best_travel.domain.repositories.TicketRepository;
import com.alejandro.best_travel.domain.repositories.TourRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final FlyRepository flyRepository;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
        log.info("Client name: " + customer.getFullName());

        var fly = flyRepository.findById(11L).orElseThrow();
        log.info("Fly: " + fly.getOriginName() + " - " + fly.getDestinyName());

        var hotel = hotelRepository.findById(3L).orElseThrow();
        log.info("Hotel: " + hotel.getName());

        var tour = TourEntity.builder()
            .customer(customer)
            .build();

        var ticket = TicketEntity.builder()
            .id(UUID.randomUUID())
            .price(fly.getPrice().multiply(BigDecimal.TEN))
            .arrivalDate(LocalDate.now())
            .departureDate(LocalDate.now())
            .purchaseDate(LocalDate.now())
            .customer(customer)
            .tour(tour)
            .fly(fly)
            .build();

        var reservation = ReservationEntity.builder()
            .id(UUID.randomUUID())
            .dateTimeReservation(LocalDateTime.now())
            .dateEnd(LocalDate.now().plusDays(2))
            .dateStart(LocalDate.now().plusDays(1))
            .hotel(hotel)
            .customer(customer)
            .tour(tour)
            .totalDays(1)
            .price(hotel.getPrice().multiply(BigDecimal.TEN))
            .build();

        tour.addReservation(reservation);
        tour.updateReservation();
        tour.addTicket(ticket);
        tour.updateTicket();
        var tourSaved = this.tourRepository.save(tour);
        Thread.sleep(8000);
        this.tourRepository.deleteById(tourSaved.getId());


    }
}