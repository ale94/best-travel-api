package ar.com.alejandro.best_travel_api.infraestructure.abstract_services;

public interface SimpleCrudService<RQ, RS, ID> {

    RS create(RQ request);

    RS read(ID id);

    void delete(ID id);

}
