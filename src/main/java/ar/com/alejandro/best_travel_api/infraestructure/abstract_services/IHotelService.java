package ar.com.alejandro.best_travel_api.infraestructure.abstract_services;

import ar.com.alejandro.best_travel_api.api.models.responses.HotelResponse;

import java.util.List;

public interface IHotelService extends CatalogService<HotelResponse> {
    List<HotelResponse> readByRating(Integer rating);
}
