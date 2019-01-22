package cps2.project.temperature.Controller;


import cps2.project.temperature.Entity.SensorData;
import cps2.project.temperature.Entity.SensorID;
import cps2.project.temperature.Repository.RepSensorData;
import cps2.project.temperature.Repository.RepSensorID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/sensors")
public class SensorRestContr {

    @Autowired
    private RepSensorID repSensorID;

    @Autowired
    private RepSensorData repSensorData;

    @Transactional(readOnly=true)
    @GetMapping("/data")
    public List<SensorID> getApiData(){
        return repSensorID.findAll();
    }

    @Transactional(readOnly=true)
    @GetMapping("/data/{id}")
    public List<SensorData> GetApiDataById(@PathVariable("id") SensorID sensorId){
        return repSensorData.findBySensoridOrderByDateDesc(sensorId);
    }

    @Transactional(readOnly=true)
    @GetMapping("/data/current")
    public List<SensorID> GetDataCurrentRoom() {
        List<SensorID> sensorIDS = repSensorID.findAll();
        for (int i = 0; i < sensorIDS.size(); i++){
            SensorID sensorID = sensorIDS.get(i);
            sensorID.setSensorDataEntity(repSensorData.findTopBySensoridOrderByDateDesc(sensorID));
            sensorIDS.set(i, sensorID);
        }
       return sensorIDS;
    }

}
