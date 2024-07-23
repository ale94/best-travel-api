package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.requests.ReservationRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.HotelResponse;
import ar.com.alejandro.best_travel_api.api.models.responses.ReservationResponse;
import ar.com.alejandro.best_travel_api.domain.entities.ReservationEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.CustomerRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.HotelRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.ReservationRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.IReservationService;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.ApiCurrencyConnectorHelper;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.BlackListHelper;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.CustomerHelper;
import ar.com.alejandro.best_travel_api.util.enums.Tables;
import ar.com.alejandro.best_travel_api.util.exceptions.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final ApiCurrencyConnectorHelper apiCurrencyConnectorHelper;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());
        var hotel = hotelRepository.findById(request.getIdHotel())
                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        var customer = customerRepository.findById(request.getIdClient())
                .orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .build();

        var reservationPersisted = this.reservationRepository.save(reservationToPersist);
        log.info("Reservation saved with id {}", reservationPersisted.getId());

        this.customerHelper.incrase(customer.getDni(), ReservationService.class);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.reservation.name()));
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {
        var reservationToUpdate = reservationRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.reservation.name()));
        var hotel = hotelRepository.findById(request.getIdHotel())
                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);
        log.info("Reservation updated with id {}", reservationUpdated.getId());

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = this.reservationRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.reservation.name()));
        this.reservationRepository.delete(reservationToDelete);
    }

    @Override
    public BigDecimal findPrice(Long hotelId, Currency currency) {
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        var priceInDollars = hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
        if (currency.equals(Currency.getInstance("USD"))) return priceInDollars;
        var currencyDTO = this.apiCurrencyConnectorHelper.getCurrency(currency);
        log.info("API currency in {}, response {}", currencyDTO.getExchangeDate().toString(), currencyDTO.getRates());
        return priceInDollars.multiply(currencyDTO.getRates().get(currency));
    }

    private ReservationResponse entityToResponse(ReservationEntity entity) {
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    public static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.20);
}
