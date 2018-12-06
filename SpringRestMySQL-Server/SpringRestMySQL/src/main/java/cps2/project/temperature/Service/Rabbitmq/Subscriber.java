package cps2.project.temperature.Service.Rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import cps2.project.temperature.Entity.SensorData;
import cps2.project.temperature.Entity.SensorID;
import cps2.project.temperature.Repository.RepSensorData;
import cps2.project.temperature.Repository.RepSensorID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;

@Component
public class Subscriber {

    @Autowired
    private RepSensorID repSensorID;

    @Autowired
    private RepSensorData repSensorData;

    @Value("${javainuse.rabbitmq.queue}")
    public String queue;

    @RabbitListener(queues = "queue1")
    public void Linestener(char[] msg){
        ObjectMapper objectMapper = new ObjectMapper();
        SensorID sensorID;
        String msg1 = String.valueOf(msg);
        System.out.println( "temp - " + String.valueOf(msg));

        try {
            sensorID = objectMapper.readValue(msg1, SensorID.class);
            SensorID sensorID1 = repSensorID.findByRoom(sensorID.getRoom());

            SensorData sensorData = objectMapper.readValue(msg1, SensorData.class);
            sensorData.setDate(new Date());

            if (!StringUtils.isEmpty(sensorID1)){
                sensorData.setSensorID(sensorID1);
                repSensorData.save(sensorData);
            }else{
                sensorID.setDescrib("example");
                repSensorID.save(sensorID);
                sensorData.setSensorID(sensorID);
                repSensorData.save(sensorData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
