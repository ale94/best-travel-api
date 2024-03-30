package ar.com.alejandro.best_travel_api;

import ar.com.alejandro.best_travel_api.domain.entities.ReservationEntity;
import ar.com.alejandro.best_travel_api.domain.entities.TicketEntity;
import ar.com.alejandro.best_travel_api.domain.entities.TourEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class BestTravelApiApplication implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final FlyRepository flyRepository;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final TourRepository tourRepository;

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
        log.info("Client name: " + customer.getFullName());

        var fly = flyRepository.findById(11L).orElseThrow();
        log.info("fly: " + fly.getOriginName() + " - " + fly.getDestinyName());

        var hotel = hotelRepository.findById(3L).orElseThrow();
        log.info("hotel: " + hotel.getName());

        var tour = TourEntity.builder()
                .customer(customer)
                .build();

        var ticket = TicketEntity.builder()
                .id(UUID.randomUUID())
                .price(fly.getPrice().multiply(BigDecimal.TEN))
                .arrivalDate(LocalDateTime.now())
                .departureDate(LocalDateTime.now())
                .purchaseDate(LocalDateTime.now())
                .customer(customer)
                .tour(tour)
                .fly(fly)
                .build();

        var reservation = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now())
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
        Thread.sleep(12000);
        this.tourRepository.deleteById(tourSaved.getId());


    }
}
