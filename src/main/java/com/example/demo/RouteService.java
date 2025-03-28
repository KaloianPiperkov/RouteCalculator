package com.example.demo;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final List<Route> routes = new ArrayList<>();

    public RouteService() { loadRoutesFromFile(); }

    private void loadRoutesFromFile() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("routes.txt");
        if (inputStream == null) throw new RuntimeException("File not found!");

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(" ");
                if (parts.length < 3) continue;
                routes.add(new Route(List.of(parts[0].trim(), parts[1].trim()), Integer.parseInt(parts[2].trim())));
            }
        }
    }

    public List<Route> getRoutes(String origin, String destination, Integer maxFlights) {
        if (origin == null || destination == null) {
            return routes.stream()
                    .sorted(Comparator.comparingInt(Route::getPrice))
                    .collect(Collectors.toList());
        }
        return findAllRoutes(origin, destination, maxFlights).stream()
                .sorted(Comparator.comparingInt(Route::getPrice))
                .collect(Collectors.toList());
    }

    private List<Route> findAllRoutes(String origin, String destination, Integer maxFlights) {
        List<Route> result = new ArrayList<>();
        findRoutesRecursive(origin, destination, new ArrayList<>(), 0, result, maxFlights, 0);
        return result;
    }

    private void findRoutesRecursive(String current, String destination, List<String> path, int price, List<Route> result, Integer maxFlights, int currentFlights) {
        path.add(current);
        if (current.equalsIgnoreCase(destination)) {
            result.add(new Route(new ArrayList<>(path), price));
        } else if (maxFlights == null || currentFlights < maxFlights) {
            for (Route route : routes) {
                if (route.getRoute().get(0).equalsIgnoreCase(current) && !path.contains(route.getRoute().get(1))) {
                    findRoutesRecursive(route.getRoute().get(1), destination, path, price + route.getPrice(), result, maxFlights, currentFlights + 1);
                }
            }
        }
        path.remove(path.size() - 1);
    }
}