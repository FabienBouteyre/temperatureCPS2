package cps2.project.temperature.Controller;

import cps2.project.temperature.Entity.Mess;
import cps2.project.temperature.Entity.OpenWeatherMap.WeatherObject;
import cps2.project.temperature.Service.ServiceOpenWeatherMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/weather")
public class WeatherRestController {

    @Autowired
    private ServiceOpenWeatherMap serviceOpenWeatherMap;

    @GetMapping(path = "/city")
    public WeatherObject GetWeather(@RequestParam(value = "city", defaultValue = "Paris") String city){

        if (!StringUtils.isEmpty(city)) {
            WeatherObject weather = serviceOpenWeatherMap.GetWeather(city);
            return weather;
        }else {
            return new WeatherObject();
        }
    }

    @PostMapping(path = "/city")
    public @ResponseBody WeatherObject PostWeather(@RequestParam(value = "city") String city){
        if (!StringUtils.isEmpty(city)) {
            WeatherObject weather = serviceOpenWeatherMap.GetWeather(city);
            return weather;
        }else {
            return new WeatherObject();
        }
    }
}
