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

        var fly = flyRepository.findById(15L).orElseThrow();
        var hotel = hotelRepository.findById(7L).orElseThrow();
        var ticket = ticketRepository.findById(UUID.fromString("32345678-1234-5678-4234-567812345678")).orElseThrow();
        var reservation = reservationRepository.findById(UUID.fromString("52345678-1234-5678-1234-567812345678")).orElseThrow();
        var customer = customerRepository.findById("BBMB771012HMCRR022").orElseThrow();

        log.info(String.valueOf(fly));
        log.info(String.valueOf(hotel));
        log.info(String.valueOf(ticket));
        log.info(String.valueOf(reservation));
        log.info(String.valueOf(customer));

    }
}
