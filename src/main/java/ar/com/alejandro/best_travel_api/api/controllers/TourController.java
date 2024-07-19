package ar.com.alejandro.best_travel_api.api.controllers;

import ar.com.alejandro.best_travel_api.api.models.requests.TourRequest;
import ar.com.alejandro.best_travel_api.api.models.responses.TourResponse;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
@Tag(name = "Tour")
public class TourController {

    private final ITourService tourService;

    @Operation(summary = "Save in system un tour based in list of hotels and flights")
    @PostMapping
    public ResponseEntity<TourResponse> post(@RequestBody TourRequest request) {
        return ResponseEntity.ok(tourService.create(request));
    }

    @Operation(summary = "Return a tour with id passed")
    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }

    @Operation(summary = "Delete a tour with id passed")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove a ticket from tour")
    @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID ticketId, @PathVariable Long tourId) {
        this.tourService.removeTicket(ticketId, tourId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a ticket from tour")
    @PatchMapping(path = "{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> postTicket(@PathVariable Long flyId, @PathVariable Long tourId) {
        var response = Collections.singletonMap("ticketId", this.tourService.addTicket(flyId, tourId));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a reservation from tour")
    @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID reservationId, @PathVariable Long tourId) {
        this.tourService.removeReservation(reservationId, tourId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a reservation from tour")
    @PatchMapping(path = "{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> postTicket(@PathVariable Long hotelId, @PathVariable Long tourId, @RequestParam Integer totalDays) {
        var response = Collections.singletonMap("reservationId", this.tourService.addReservation(hotelId, tourId,
                totalDays));
        return ResponseEntity.ok(response);
    }

}
