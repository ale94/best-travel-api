package ar.com.alejandro.best_travel_api.api.models.requests;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {

    private String customerId;
    private Set<TourFlyRequest> flights;
    private Set<TourHotelRequest> hotels;

    @Email(message = "Invalid email")
    private String email;
}
