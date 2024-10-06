package ar.com.alejandro.best_travel_api.domain.repositories.mongo;

import ar.com.alejandro.best_travel_api.domain.entities.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository<AppUserDocument, String> {
}
