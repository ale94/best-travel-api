package ar.com.alejandro.best_travel_api.infraestructure.abstract_services;

import ar.com.alejandro.best_travel_api.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
