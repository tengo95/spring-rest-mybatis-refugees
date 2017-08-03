package mybatis.controllers;

import mybatis.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tanerali on 02/08/2017.
 */
@Controller
@RequestMapping("/weather")
public class WeatherMVC {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WeatherService weatherService;

    @RequestMapping("/weeklyDB")
    public String getDBWeeklyForecast (Model model) {

//        ArrayList<WeeklyForecast> weeklyForecast = weatherService.getDBWeeklyForecast();

        model.addAttribute("weeklyForecasts", weatherService.getDBWeeklyForecast());

        return "weeklyForecast";
    }

}
