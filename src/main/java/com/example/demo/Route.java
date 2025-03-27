package com.example.demo;

import java.util.List;

public class Route {
    private List<String> route;
    private int price;

    public Route(List<String> route, int price) {
        this.route = route;
        this.price = price;
    }

    public List<String> getRoute() { return route; }
    public int getPrice() { return price; }
}
