package ar.com.alejandro.best_travel_api;

import ar.com.alejandro.best_travel_api.domain.repositories.FlyRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BestTravelApiApplication implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private FlyRepository flyRepository;

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /*var fly = flyRepository.findById(155L).orElseThrow();
        var hotel = hotelRepository.findById(75L).orElseThrow();

        log.info(String.valueOf(fly));
        log.info(String.valueOf(hotel));*/

    }
}
