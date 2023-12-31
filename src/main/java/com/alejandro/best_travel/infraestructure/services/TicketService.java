package com.alejandro.best_travel.infraestructure.services;

import com.alejandro.best_travel.api.models.request.TicketRequest;
import com.alejandro.best_travel.api.models.responses.FlyResponse;
import com.alejandro.best_travel.api.models.responses.TicketResponse;
import com.alejandro.best_travel.domain.entities.TicketEntity;
import com.alejandro.best_travel.domain.repositories.CustomerRepository;
import com.alejandro.best_travel.domain.repositories.FlyRepository;
import com.alejandro.best_travel.domain.repositories.TicketRepository;
import com.alejandro.best_travel.infraestructure.abstract_services.ITicketService;
import com.alejandro.best_travel.util.BestTravelUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            .price(fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
            .purchaseDate(LocalDate.now())
            .arrivalDate(BestTravelUtil.getRandomLatter())
            .departureDate(BestTravelUtil.getRandomSoon())
            .build();

        var ticketPersisted = ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id: {}", ticketPersisted.getId());

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
        ticketToUpdate.setPrice(
            fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());

        var ticketUpdated = ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated with id: {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findPrice(Long flyId) {
        var fly = flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
    }

    // MAPPER
    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    private static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

}
