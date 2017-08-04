package mybatis.rest_controllers;

import mybatis.model.DarkSkyWeather.DayInWeeklyForecast;
import mybatis.model.DarkSkyWeather.Forecast;
import mybatis.model.DarkSkyWeather.HistoricalDaySummary;
import mybatis.model.DarkSkyWeather.HourlyAverage;
import mybatis.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by tanerali on 26/07/2017.
 */

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    WeatherService weatherService;

    @RequestMapping("/current")
    public Forecast getCurrentForecast(@RequestParam(value="latitude")double latitude,
                                       @RequestParam(value="longitude")double longitude) {
        return weatherService.getCurrentForecast(latitude, longitude);
    }

    @RequestMapping("/current/average")
    public HourlyAverage getCurrentHourlyAverage(@RequestParam(value="latitude")double latitude,
                                                 @RequestParam(value="longitude")double longitude) {
        return weatherService.getCurrentHourlyAverage(latitude, longitude);
    }




    @RequestMapping("/weekly")
    public ArrayList<DayInWeeklyForecast> getWeeklyForecast (@RequestParam(value="latitude")double latitude,
                                                             @RequestParam(value="longitude")double longitude,
                                                             Model model) {

        ArrayList<DayInWeeklyForecast> dayInWeeklyForecast = weatherService.getWeeklyForecast(latitude, longitude);

        return dayInWeeklyForecast;
    }



    @RequestMapping("/historical")
    public Forecast getHistoricalWeather(@RequestParam(value="latitude")double latitude,
                                         @RequestParam(value="longitude")double longitude,
                                         @RequestParam(value = "timeMM/DD/YYYY")String time) {
        return weatherService.getHistoricalWeather(latitude, longitude, time);
    }

    @RequestMapping("/historical/summary")
    public ArrayList<HistoricalDaySummary> getHistoricalSummary(@RequestParam(value="latitude")double latitude,
                                                                @RequestParam(value="longitude")double longitude,
                                                                @RequestParam(value = "timeMM/DD/YYYY")String time) {
        return weatherService.getHistoricalSummary(latitude, longitude, time);
    }

    //Add weekly forecast to database
    @RequestMapping(method = RequestMethod.POST, value = "/weeklyDB")
    public ArrayList<DayInWeeklyForecast> addNewWeeklyForecast(@RequestParam(value="latitude")double latitude,
                                                               @RequestParam(value="longitude")double longitude) {
        return weatherService.addNewWeeklyForecast(latitude, longitude);
    }
}
