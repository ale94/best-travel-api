package ar.com.alejandro.best_travel_api.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourResponse implements Serializable {
    private Long id;
    private List<UUID> ticketIds;
    private List<UUID> reservationIds;
}
