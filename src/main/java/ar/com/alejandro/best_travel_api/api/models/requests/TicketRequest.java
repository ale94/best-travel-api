package ar.com.alejandro.best_travel_api.api.models.requests;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketRequest implements Serializable {

    private String idClient;
    private Long idFly;

    @Email(message = "Invalid email")
    private String email;
}
