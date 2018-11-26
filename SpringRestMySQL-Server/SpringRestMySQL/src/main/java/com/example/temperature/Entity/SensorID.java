package com.example.temperature.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sensor")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorID {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("room")
    @Column(name = "room")
    private String room;

    @Column(name = "describ")
    private String describ;

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
}
