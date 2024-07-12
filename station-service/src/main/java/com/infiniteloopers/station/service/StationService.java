package com.infiniteloopers.station.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteloopers.station.model.Booking;
import com.infiniteloopers.station.model.Station;
import com.infiniteloopers.station.repository.StationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class StationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StationRepository stationRepository;

    private List<Station> mockStations;

    @PostConstruct
    public void loadMockData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Station>> typeReference = new TypeReference<List<Station>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/mock-stations.json");
        try {
            mockStations = objectMapper.readValue(inputStream, typeReference);
            mockStations.forEach(stationRepository::save);
            System.out.println("Mock stations loaded successfully");
        } catch (IOException e) {
            System.out.println("Unable to load mock stations: " + e.getMessage());
        }
    }

    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElse(null);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallbackBookings")
    @TimeLimiter(name = "bookingServiceCircuitBreaker")
    public List<Booking> getBookingsByStationId(Long stationId) {
        String url = "http://localhost:8083/bookings/station?stationId=" + stationId;
        Booking[] bookings = restTemplate.getForObject(url, Booking[].class);
        if (bookings == null) {
            throw new RuntimeException("Failed to fetch bookings for station ID: " + stationId);
        }
        return Arrays.asList(bookings);
    }

    @CircuitBreaker(name = "bookingServiceCircuitBreaker", fallbackMethod = "fallbackUpdateStatus")
    @TimeLimiter(name = "bookingServiceCircuitBreaker")
    public void updateBookingStatus(Long bookingId, String status) {
        String url = "http://localhost:8083/bookings/updateStatus/" + bookingId + "/" + status;
        restTemplate.postForEntity(url, null, Void.class);
    }

    // Fallback method for getBookingsByStationId
    public List<Booking> fallbackBookings(Long stationId, Throwable t) {
        System.out.println("Fallback executed for getBookingsByStationId: " + t.getMessage());
        return List.of(); // Returning an empty list as fallback
    }

    // Fallback method for updateBookingStatus
    public void fallbackUpdateStatus(Long bookingId, String status, Throwable t) {
        System.out.println("Fallback executed for updateBookingStatus: " + t.getMessage());
        // Implement appropriate fallback logic, such as logging or notifying a service
    }
}
