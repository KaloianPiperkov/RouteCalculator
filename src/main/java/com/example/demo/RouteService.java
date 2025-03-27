package com.example.demo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RouteService {

    public List<Route> findRoutes(String origin, String destination) {
        // Hardcoded example data - ideally, this would come from a database
        List<Route> routes = new ArrayList<>();

        routes.add(new Route(Arrays.asList("SOF", "LON", "MLE"), 30));
        routes.add(new Route(Arrays.asList("SOF", "MLE"), 70));

        // Sort by price in ascending order
        routes.sort((r1, r2) -> Integer.compare(r1.getPrice(), r2.getPrice()));

        return routes;
    }
}
