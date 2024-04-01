package ar.com.alejandro.best_travel_api.infraestructure.abstract_services;

import ar.com.alejandro.best_travel_api.api.models.requests.ReservationRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.ReservationResponse;

import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID> {
}
