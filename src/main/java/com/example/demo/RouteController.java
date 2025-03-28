package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/getRoutes")
    public List<Route> getRoutes(@RequestBody RouteRequest request) {
        if (request.getOrigin() == null || request.getDestination() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Origin and Destination are required");
        }
        return routeService.getRoutes(request.getOrigin(), request.getDestination(), request.getMax_flights());
    }
}