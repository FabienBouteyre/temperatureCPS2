package cps2.project.temperature.Controller;


import cps2.project.temperature.Entity.SensorID;
import cps2.project.temperature.Repository.RepSensorData;
import cps2.project.temperature.Repository.RepSensorID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/sensors")
public class SensorContr {

    @Autowired
    private RepSensorID repSensorID;

    @Autowired
    private RepSensorData repSensorData;

    @GetMapping("/data")
    public String GetData(Model model) {
        model.addAttribute("sensors", repSensorID.findAll());
        return "data";
    }

    @GetMapping("/data/{id}")
    public String GetDataId(@PathVariable("id") SensorID sensorID, Model model) {
        model.addAttribute("data", repSensorData.findBySensoridOrderByDateDesc(sensorID));
        model.addAttribute("sensor", sensorID);
        return "data2";
    }

    @GetMapping("/data/current")
    public String GetDataCurrentRoom(Model model) {
        List<SensorID> sensorIDS = repSensorID.findAll();
        for (int i = 0; i < sensorIDS.size(); i++){
            SensorID sensorID = sensorIDS.get(i);
            sensorID.setSensorDataEntity(repSensorData.findTopBySensoridOrderByDateDesc(sensorID));
            sensorIDS.set(i, sensorID);
        }
        model.addAttribute("sensors", sensorIDS);
        return "currentdata";
    }



}
