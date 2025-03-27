package com.example.demo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping(value = "/getRoutes", method = RequestMethod.POST)
    public List<Route> getRoutes(@RequestBody RouteRequest request) {
        return routeService.getRoutes(request.getOrigin(), request.getDestination());
    }
}