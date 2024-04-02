package ar.com.alejandro.best_travel_api.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {

    private String customerId;
    private List<TourFlyRequest> flights;
    private List<TourHotelRequest> hotels;
}
