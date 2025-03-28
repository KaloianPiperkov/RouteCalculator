package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteServiceTest {

	private RouteService routeService;

	@BeforeEach
	void setUp() {
		routeService = new RouteService();
	}

	@Test
	void testGetRoutesWithDirectFlight() {
		List<Route> routes = routeService.getRoutes("SOF", "MLE", 1);
		assertEquals(1, routes.size());
		assertEquals(70, routes.get(0).getPrice());
	}

	@Test
	void testGetRoutesWithMultipleFlights() {
		List<Route> routes = routeService.getRoutes("SOF", "MLE", 2);
		assertFalse(routes.isEmpty());
		assertTrue(routes.stream().anyMatch(route -> route.getPrice() == 30));
	}

	@Test
	void testGetRoutesWithNoLimit() {
		List<Route> routes = routeService.getRoutes("SOF", "MLE", null);
		assertFalse(routes.isEmpty());
		assertTrue(routes.stream().anyMatch(route -> route.getPrice() == 30));
	}

	@Test
	void testGetAllRoutesSortedByPrice() {
		List<Route> routes = routeService.getRoutes(null, null, null);
		assertFalse(routes.isEmpty());
		assertEquals(10, routes.get(0).getPrice());
	}

	@Test
	void testGetRoutesWithNoRoutesAvailable() {
		List<Route> routes = routeService.getRoutes("XYZ", "ABC", null);
		assertTrue(routes.isEmpty());
	}

	@Test
	void testGetRoutesWithInvalidOriginOrDestination() {
		List<Route> routes = routeService.getRoutes("XYZ", "MLE", null);
		assertTrue(routes.isEmpty());

		routes = routeService.getRoutes("SOF", "ABC", null);
		assertTrue(routes.isEmpty());
	}

	@Test
	void testGetRoutesWithMaxFlightsZero() {
		List<Route> routes = routeService.getRoutes("SOF", "MLE", 0);
		assertTrue(routes.isEmpty());
	}

	@Test
	void testGetRoutesWithCircularRoutes() {
		// Assuming circular routes are added to the routes.txt for this test
		List<Route> routes = routeService.getRoutes("SOF", "SOF", null);
		assertTrue(routes.isEmpty());
	}

	@Test
	void testGetRoutesWithLargeNumberOfRoutes() {
		// Assuming a large number of routes are added to the routes.txt for this test
		List<Route> routes = routeService.getRoutes("SOF", "MLE", null);
		assertFalse(routes.isEmpty());
	}
}