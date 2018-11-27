package cps2.project.temperature.Repository;


import cps2.project.temperature.Entity.SensorData;
import cps2.project.temperature.Entity.SensorID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepSensorData extends JpaRepository<SensorData, Long> {

    SensorData findTopByOrderByIdDesc();

    List<SensorData> findBySensoridOrderByDateDesc(SensorID sensorID);

    SensorData findTopBySensoridOrderByDateDesc(SensorID sensorID);
//    List<SensorData> findSensorDataBySensorid(Long id);
}
