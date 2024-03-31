package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.requests.TicketRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.FlyResponse;
import ar.com.alejandro.best_travel_api.api.models.responses.TicketResponse;
import ar.com.alejandro.best_travel_api.domain.entities.TicketEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.CustomerRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.FlyRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.TicketRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.ITicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse create(TicketRequest request) {
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(BigDecimal.valueOf(0.25)))
                .purchaseDate(LocalDateTime.now())
                .arrivalDate(LocalDateTime.now())
                .departureDate(LocalDateTime.now())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id {}", ticketPersisted.getId());

        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = ticketRepository.findById(id).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow();
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(BigDecimal.valueOf(0.25));
        ticketToUpdate.setDepartureDate(LocalDateTime.now());
        ticketToUpdate.setArrivalDate(LocalDateTime.now());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {

    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }
}
