package ar.com.alejandro.best_travel_api.infraestructure.services;

import ar.com.alejandro.best_travel_api.api.models.requests.TicketRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.FlyResponse;
import ar.com.alejandro.best_travel_api.api.models.responses.TicketResponse;
import ar.com.alejandro.best_travel_api.domain.entities.TicketEntity;
import ar.com.alejandro.best_travel_api.domain.repositories.CustomerRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.FlyRepository;
import ar.com.alejandro.best_travel_api.domain.repositories.TicketRepository;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.ITicketService;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.BlackListHelper;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.CustomerHelper;
import ar.com.alejandro.best_travel_api.infraestructure.helpers.EmailHelper;
import ar.com.alejandro.best_travel_api.util.BestTravelUtil;
import ar.com.alejandro.best_travel_api.util.enums.Tables;
import ar.com.alejandro.best_travel_api.util.exceptions.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;

    @Override
    public TicketResponse create(TicketRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());
        var fly = flyRepository.findById(request.getIdFly())
                .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
        var customer = customerRepository.findById(request.getIdClient())
                .orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
                .purchaseDate(LocalDateTime.now())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .departureDate(BestTravelUtil.getRandomSoon())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id {}", ticketPersisted.getId());

        if (Objects.nonNull(request.getEmail())) {
            this.emailHelper.sendMail(request.getEmail(), customer.getFullName(), Tables.ticket.name());
        }

        this.customerHelper.incrase(customer.getDni(), TicketService.class);

        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = ticketRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
        var fly = flyRepository.findById(request.getIdFly())
                .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findPrice(Long flyId) {
        var fly = flyRepository.findById(flyId)
                .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
        return fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    public static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

}
