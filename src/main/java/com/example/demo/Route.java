package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Route {
    private List<String> route;
    private int price;
}
