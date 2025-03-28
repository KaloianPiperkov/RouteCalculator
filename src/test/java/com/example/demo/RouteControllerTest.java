package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RouteService routeService;

    private List<Route> mockRoutes;

    @BeforeEach
    void setUp() {
        mockRoutes = List.of(
                new Route(List.of("SOF", "MLE"), 70),
                new Route(List.of("SOF", "IST", "CMB", "MLE"), 70)
        );
    }

    @Test
    void testGetRoutes() throws Exception {
        when(routeService.getRoutes("SOF", "MLE", 2)).thenReturn(mockRoutes);

        mockMvc.perform(post("/api/routes/getRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"SOF\",\"destination\":\"MLE\",\"max_flights\":2}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"route\":[\"SOF\",\"MLE\"],\"price\":70},{\"route\":[\"SOF\",\"IST\",\"CMB\",\"MLE\"],\"price\":70}]"));
    }

    @Test
    void testGetRoutesWithMissingParameters() throws Exception {
        mockMvc.perform(post("/api/routes/getRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"SOF\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetRoutesWithInvalidJsonFormat() throws Exception {
        mockMvc.perform(post("/api/routes/getRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\": \"SOF\", \"destination\": \"MLE\"")) // Missing closing brace
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetRoutesWithMaxFlightsZero() throws Exception {
        when(routeService.getRoutes("SOF", "MLE", 0)).thenReturn(List.of());

        mockMvc.perform(post("/api/routes/getRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"SOF\",\"destination\":\"MLE\",\"max_flights\":0}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetRoutesWithNonExistentRoutes() throws Exception {
        when(routeService.getRoutes("XYZ", "ABC", 2)).thenReturn(List.of());

        mockMvc.perform(post("/api/routes/getRoutes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"XYZ\",\"destination\":\"ABC\",\"max_flights\":2}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}