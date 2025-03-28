package com.example.demo;

public class RouteRequest {
    private String origin;
    private String destination;
    private Integer max_flights;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getMax_flights() {
        return max_flights;
    }

    public void setMax_flights(Integer max_flights) {
        this.max_flights = max_flights;
    }
}