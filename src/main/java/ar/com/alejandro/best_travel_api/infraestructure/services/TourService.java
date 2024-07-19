package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.requests.TourRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.TourResponse;
import ar.com.alejandro.best_travel_api.domain.entities.*;
import ar.com.alejandro.best_travel_api.domain.repositories.CustomerRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.FlyRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.HotelRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.TourRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.ITourService;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.BlackListHelper;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.CustomerHelper;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.TourHelper;
import ar.com.alejandro.best_travel_api.util.enums.Tables;
import ar.com.alejandro.best_travel_api.util.exceptions.IdNotFoundException;
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
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    @Override
    public TourResponse create(TourRequest request) {

        blackListHelper.isInBlackListCustomer(request.getCustomerId());

        var customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow(() -> new IdNotFoundException(Tables.fly.name()))));

        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel ->
                hotels.put(this.hotelRepository.findById(hotel.getId())
                                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name())),
                        hotel.getTotalDays()));

        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();
        var tourSaved = this.tourRepository.save(tourToSave);

        this.customerHelper.incrase(customer.getDni(), TourService.class);

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
        var tourFromDB = this.tourRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
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
        var tourToDelete = this.tourRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId)
                .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId)
                .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        var fly = flyRepository.findById(flyId)
                .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
        var ticket = tourHelper.createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId)
                .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long hotelId, Long tourId, Integer totalDays) {
        var tourUpdate = this.tourRepository.findById(tourId)
                .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        var reservation = tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }
}
