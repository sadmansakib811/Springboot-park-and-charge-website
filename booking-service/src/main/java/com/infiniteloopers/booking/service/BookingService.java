package com.infiniteloopers.booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteloopers.booking.model.Booking;
import com.infiniteloopers.booking.model.BookingStatus;
import com.infiniteloopers.booking.model.Station;
import com.infiniteloopers.booking.repository.BookingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    private List<Booking> mockBookings;

    @PostConstruct
    public void loadMockData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Booking>> typeReference = new TypeReference<List<Booking>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/mock-bookings.json");
        try {
            mockBookings = objectMapper.readValue(inputStream, typeReference);
            mockBookings.forEach(bookingRepository::save);
            System.out.println("Mock bookings loaded successfully");
        } catch (IOException e) {
            System.out.println("Unable to load mock bookings: " + e.getMessage());
        }
    }

    public Booking saveBooking(Booking booking) {
        populateAdditionalDetails(booking); // Ensure details are populated before saving
        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            populateAdditionalDetails(booking);
            System.out.println("Booking Status: " + booking.getStatus());
        }
        return booking;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        bookings.forEach(this::populateAdditionalDetails);
        return bookings;
    }

    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = getBookingById(bookingId);
        if (booking != null) {
            booking.setStatus(status);
            saveBooking(booking);
        }
    }

    @CircuitBreaker(name = "stationServiceCircuitBreaker", fallbackMethod = "fallbackStationDetails")
    @TimeLimiter(name = "stationServiceCircuitBreaker")
    private void populateAdditionalDetails(Booking booking) {
        String stationServiceUrl = "http://localhost:8082/stations/" + booking.getStationId();
        try {
            Station station = restTemplate.getForObject(stationServiceUrl, Station.class);
            if (station != null) {
                booking.setStationName(station.getName());
                booking.setTotalPrice(calculateTotalPrice(booking, station.getPricePerHour()));
                System.out.println("Station details fetched successfully: " + station);
                System.out.println("Calculated Total Price: " + booking.getTotalPrice());
            } else {
                booking.setStationName("Station not found");
                booking.setTotalPrice(0.0);
            }
        } catch (Exception e) {
            booking.setStationName("Station not found");
            booking.setTotalPrice(0.0);
            e.printStackTrace();
        }
    }

    private Double calculateTotalPrice(Booking booking, double pricePerHour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(booking.getStartTime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(booking.getEndTime(), formatter);
        long hours = Duration.between(startTime, endTime).toHours();
        return hours * pricePerHour;
    }

    public List<Booking> getBookingsByStationId(Long stationId) {
        List<Booking> bookings = bookingRepository.findByStationId(stationId);
        bookings.forEach(this::populateAdditionalDetails);
        return bookings;
    }

    // Fallback method for populateAdditionalDetails
    public void fallbackStationDetails(Booking booking, Throwable t) {
        booking.setStationName("Fallback - Station details not available");
        booking.setTotalPrice(0.0);
        System.out.println("Fallback executed: " + t.getMessage());
    }
}
