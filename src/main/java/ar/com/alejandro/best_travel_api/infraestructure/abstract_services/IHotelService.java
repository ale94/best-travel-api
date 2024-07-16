package ar.com.alejandro.best_travel_api.infraestructure.abstract_services;

import ar.com.alejandro.best_travel_api.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse> {
    Set<HotelResponse> readByRating(Integer rating);
}
