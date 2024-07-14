package ar.com.alejandro.best_travel_api.infraestructure.abstract_services;

import jakarta.persistence.Id;

public interface SimpleCrudService<RQ, RS, ID> {

    RS create(RQ request);

    RS read(ID id);

    void delete(Id id);

}
