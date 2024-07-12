package com.infiniteloopers.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infiniteloopers.user.model.AppUser;
import com.infiniteloopers.user.repository.UserRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
 private List<AppUser> mockUsers;

    @PostConstruct
    public void loadMockData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<AppUser>> typeReference = new TypeReference<List<AppUser>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/mock-users.json");
        try {
            mockUsers = objectMapper.readValue(inputStream, typeReference);
            mockUsers.forEach(userRepository::save);
            System.out.println("Mock users loaded successfully");
        } catch (IOException e) {
            System.out.println("Unable to load mock users: " + e.getMessage());
        }
    }
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
     @CircuitBreaker(name = "externalServiceCircuitBreaker", fallbackMethod = "fallbackGetExternalData")
    @TimeLimiter(name = "externalServiceCircuitBreaker")
    public String getExternalData() {
        // Simulate an external call
        throw new RuntimeException("Simulated external service failure");
    }

    public String fallbackGetExternalData(Throwable t) {
        System.out.println("Fallback executed for getExternalData: " + t.getMessage());
        return "Fallback response";
    }
}
