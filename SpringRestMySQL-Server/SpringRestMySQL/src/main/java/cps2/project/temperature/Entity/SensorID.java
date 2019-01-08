package cps2.project.temperature.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sensor")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorID {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room")
    private String room;

    @Column(name = "describ")
    private String describ;

    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    @Transient
    private SensorData sensorDataEntity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sensorid", orphanRemoval = true)
    private List<SensorData> sensorData;

    public SensorID() {
    }

    public SensorID(String room) {
        this.room = room;
    }

    public SensorID(String room, String describ) {
        this.room = room;
        this.describ = describ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

//    public List<SensorData> getSensorData() {
//        return sensorData;
//    }

    public void setSensorData(List<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    public SensorData getSensorDataEntity() {
        return sensorDataEntity;
    }

    public void setSensorDataEntity(SensorData sensorDataEntity) {
        this.sensorDataEntity = sensorDataEntity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<SensorData> getSensorData() {
        return sensorData;
    }

    @Override
    public String toString() {
        return "SensorID{" +
                "id=" + id +
                ", room='" + room + '\'' +
                ", describ='" + describ + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
