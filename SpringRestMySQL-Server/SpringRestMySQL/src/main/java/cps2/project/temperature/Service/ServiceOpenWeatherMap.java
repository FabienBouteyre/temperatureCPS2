package cps2.project.temperature.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cps2.project.temperature.Entity.OpenWeatherMap.Weather;
import cps2.project.temperature.Entity.OpenWeatherMap.WeatherObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class ServiceOpenWeatherMap {

    @Value("${open-weather-map.key}")
    private String key;

    public WeatherObject GetWeather(String city){
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherObject weather = null;
        if (!StringUtils.isEmpty(city)) {
            try {
//                data = restTemplate.getForObject(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, key), String.class);
                ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, key), String.class);
                weather = objectMapper.readValue(response.getBody(), WeatherObject.class);
            } catch (IOException e) {
                e.printStackTrace();
                weather = new WeatherObject();
            } catch (HttpClientErrorException e) {
                System.out.println("Error http");
                weather = new WeatherObject();
            }

        }
        return weather;
    }



}
