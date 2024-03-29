package ar.com.alejandro.best_travel_api;

import ar.com.alejandro.best_travel_api.domain.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        var hotel = hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678")).orElseThrow();
        log.info(String.valueOf(hotel));

    }
}
