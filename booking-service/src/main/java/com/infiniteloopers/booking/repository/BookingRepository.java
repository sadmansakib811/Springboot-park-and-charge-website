package com.infiniteloopers.booking.repository;

import com.infiniteloopers.booking.model.Booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStationId(Long stationId);
}
