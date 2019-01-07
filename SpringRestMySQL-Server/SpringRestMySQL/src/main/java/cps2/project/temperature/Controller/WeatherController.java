package cps2.project.temperature.Controller;

import cps2.project.temperature.Entity.OpenWeatherMap.WeatherObject;
import cps2.project.temperature.Service.ServiceOpenWeatherMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping(path = "/weather")
public class WeatherController {

    @Autowired
    private ServiceOpenWeatherMap serviceOpenWeatherMap;

    @GetMapping(path = "/city")
    public String GetWeather(Model model){
        return "weather";
    }

    @PostMapping(path = "/city")
    public String PostWeather(@RequestParam("city") String city, Model model){
        if (!StringUtils.isEmpty(city)) {
            WeatherObject weather = serviceOpenWeatherMap.GetWeather(city);
            model.addAttribute("mess", weather);
        }

        return "weather";
    }


}
