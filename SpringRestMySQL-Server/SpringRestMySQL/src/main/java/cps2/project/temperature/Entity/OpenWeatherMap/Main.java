package cps2.project.temperature.Entity.OpenWeatherMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    @JsonProperty("temp")
    private float temp;

    @JsonProperty("presure")
    private Integer presure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("temp_min")
    private float temp_min;

    @JsonProperty("temp_max")
    private float temp_max;

    public Main() {
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public Integer getPresure() {
        return presure;
    }

    public void setPresure(Integer presure) {
        this.presure = presure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }
}
