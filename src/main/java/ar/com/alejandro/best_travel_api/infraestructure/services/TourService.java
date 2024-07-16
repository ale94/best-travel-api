package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.requests.TourRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.TourResponse;
import ar.com.alejandro.best_travel_api.domain.entities.*;
import ar.com.alejandro.best_travel_api.domain.repositories.CustomerRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.FlyRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.HotelRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.TourRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.ITourService;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.TourHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;

    @Override
    public TourResponse create(TourRequest request) {

        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow()));

        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel ->
                hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays()));

        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();
        var tourSaved = this.tourRepository.save(tourToSave);

        return TourResponse.builder()
                .id(tourSaved.getId())
                .ticketIds(tourSaved.getTickets()
                        .stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .reservationIds(tourSaved.getReservations()
                        .stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDB = this.tourRepository.findById(id).orElseThrow();
        return TourResponse.builder()
                .id(tourFromDB.getId())
                .ticketIds(tourFromDB.getTickets()
                        .stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .reservationIds(tourFromDB.getReservations()
                        .stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow();
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long hotelId, Long tourId) {
        return null;
    }
}
