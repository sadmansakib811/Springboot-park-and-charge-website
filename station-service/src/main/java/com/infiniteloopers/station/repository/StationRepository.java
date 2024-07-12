package com.infiniteloopers.station.repository;

import com.infiniteloopers.station.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
