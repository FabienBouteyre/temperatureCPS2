package cps2.project.temperature.Controller;

import cps2.project.temperature.Entity.Mess;
import cps2.project.temperature.Entity.OpenWeatherMap.Weather;
import cps2.project.temperature.Entity.OpenWeatherMap.WeatherObject;
import cps2.project.temperature.Service.ServiceOpenWeatherMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/weather")
public class WeatherRestController {

    @Autowired
    private ServiceOpenWeatherMap serviceOpenWeatherMap;

    @GetMapping(path = "/city")
    public Mess GetWeather(){
        return new Mess("This service support post method with param city");
    }

    @PostMapping(path = "/city")
    public WeatherObject PostWeather(@RequestParam("city") String city){
        if (!StringUtils.isEmpty(city)) {
            WeatherObject weather = serviceOpenWeatherMap.GetWeather(city);
            return weather;
        }else {
            return new WeatherObject();
        }

    }


}
