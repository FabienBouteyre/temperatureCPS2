package cps2.project.temperature.Repository;


import cps2.project.temperature.Entity.SensorID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepSensorID extends JpaRepository<SensorID, Long> {

    SensorID findByRoom(String room);
    SensorID findByAddress(String mac);
}
