package com.alejandro.best_travel;

import com.alejandro.best_travel.domain.repositories.CustomerRepository;
import com.alejandro.best_travel.domain.repositories.FlyRepository;
import com.alejandro.best_travel.domain.repositories.HotelRepository;
import com.alejandro.best_travel.domain.repositories.ReservationRepository;
import com.alejandro.best_travel.domain.repositories.TicketRepository;
import com.alejandro.best_travel.domain.repositories.TourRepository;
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

        var fly = flyRepository.findById(15L).orElseThrow();
        var hotel = hotelRepository.findById(8L).orElseThrow();
        var ticket = ticketRepository.findById(
            UUID.fromString("32345678-1234-5678-4234-567812345678")).orElseThrow();
        var reservation = reservationRepository.findById(
            UUID.fromString("52345678-1234-5678-1234-567812345678")).orElseThrow();
        var customer = customerRepository.findById(
            "BBMB771012HMCRR022").orElseThrow();

        log.info(String.valueOf(fly));
        log.info(String.valueOf(hotel));
        log.info(String.valueOf(ticket));
        log.info(String.valueOf(reservation));
        log.info(String.valueOf(customer));
    }
}
