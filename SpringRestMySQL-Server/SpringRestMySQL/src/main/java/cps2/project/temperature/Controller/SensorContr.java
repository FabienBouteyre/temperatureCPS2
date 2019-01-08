package cps2.project.temperature.Controller;


import cps2.project.temperature.Entity.SensorID;
import cps2.project.temperature.Repository.RepSensorData;
import cps2.project.temperature.Repository.RepSensorID;
import cps2.project.temperature.Service.Rabbitmq.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping("/data/manage")
    public String GetManageData(Model model){

        model.addAttribute("sensors", Subscriber.sensorIDs);
        return "sensormanage";
    }

    @PostMapping("/data/edit")
    public String PostEditSensor(@RequestParam(value = "id", required = false) SensorID sensorID, @RequestParam HashMap<String, String> map, Model model){
        if (!StringUtils.isEmpty(sensorID) && Subscriber.sensorIDs.stream().anyMatch(sensor -> sensorID.getAddress().equals(sensor.getAddress()))) {
            model.addAttribute("sensor", sensorID);
            if (!StringUtils.isEmpty(map.get("room"))) {
                sensorID.setRoom(map.get("room"));
                if (!StringUtils.isEmpty(map.get("description")))
                    sensorID.setDescrib(map.get("description"));
                repSensorID.save(sensorID);
            }
            model.addAttribute("mess", "Updated");
            return "sensoredit";
        }else
            return "sensormanage";
    }

}
