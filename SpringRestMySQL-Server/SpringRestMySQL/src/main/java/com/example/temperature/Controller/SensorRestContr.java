package com.example.temperature.Controller;


import com.example.temperature.Entity.SensorData;
import com.example.temperature.Entity.SensorID;
import com.example.temperature.Repository.RepSensorData;
import com.example.temperature.Repository.RepSensorID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/sensors")
public class SensorRestContr {

    @Autowired
    private RepSensorID repSensorID;

    @Autowired
    private RepSensorData repSensorData;

    @GetMapping("/data")
    public List<SensorID> getApiData(){
        return repSensorID.findAll();
    }

    @GetMapping("/data/{id}")
    public List<SensorData> GetApiDataById(@PathVariable("id") SensorID sensorId){
        return repSensorData.findBySensoridOrderByDateDesc(sensorId);
    }

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
