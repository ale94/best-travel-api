package ar.com.alejandro.best_travel_api.api.controllers;

import ar.com.alejandro.best_travel_api.api.models.responses.FlyResponse;
import ar.com.alejandro.best_travel_api.infraestructure.abstract_services.IFlyService;
import ar.com.alejandro.best_travel_api.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "fly")
@RequiredArgsConstructor
public class FlyController {

    private final IFlyService flyService;

    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.flyService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
