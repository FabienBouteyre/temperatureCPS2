package cps2.project.temperature.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sensordata")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("temp")
    @Column(name = "value")
    private String value;

    @JsonProperty("light")
    @Column(name = "light")
    private String light;

    @JsonProperty("hmdt")
    @Column(name = "hmdt")
    private String hmdt;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private SensorID sensorid;

    public SensorData() {
    }

    public SensorData(String value, String light, String hmdt) {
        this.value = value;
        this.light = light;
        this.hmdt = hmdt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getHmdt() {
        return hmdt;
    }

    public void setHmdt(String hmdt) {
        this.hmdt = hmdt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public SensorID getSensorID() {
////        return sensorid;
////    }

    public void setSensorID(SensorID sensorID) {
        this.sensorid = sensorID;
    }
}
