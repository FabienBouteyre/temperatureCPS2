package com.example.temperature.Repository;

import com.example.temperature.Entity.SensorData;
import com.example.temperature.Entity.SensorID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepSensorID extends JpaRepository<SensorID, Long> {

    SensorID findByRoom(String room);
}
