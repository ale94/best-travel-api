package ar.com.alejandro.best_travel_api.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "report")
@RequiredArgsConstructor
@Tag(name = "Report")
public class ReportController {
}
