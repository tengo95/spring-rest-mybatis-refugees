package mybatis.services;

import mybatis.mappers.WeatherMapper;
import mybatis.model.DarkSkyWeather.Forecast;
import mybatis.model.DarkSkyWeather.HistoricalDaySummary;
import mybatis.model.DarkSkyWeather.HourlyAverage;
import mybatis.model.DarkSkyWeather.WeeklyForecast;
import mybatis.util.DateUnix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by tanerali on 26/07/2017.
 */
@Service
public class WeatherService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WeatherMapper weatherMapper;


    public Forecast getCurrentForecast(double latitude, double longitude) {
        Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);
        return forecast;
    }


    public HourlyAverage getCurrentHourlyAverage(double latitude, double longitude) {
        Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);


        double precipIntensity = 0;
        double precipProbability = 0;
        double temperature = 0;
        double apparentTemperature = 0;
        double dewPoint = 0;
        double humidity = 0;
        double windSpeed = 0;
        double windGust = 0;
        int windBearing = 0;
        double cloudCover = 0;
        double pressure = 0;
        double ozone = 0;
        int uvIndex = 0;

        int count = 1;

        mybatis.model.DarkSkyWeather.Data[] data = forecast.getHourly().getData();

        for (int i=0; i<data.length; i++) {
            precipIntensity += data[i].getPrecipIntensity();
            precipProbability += data[i].getPrecipProbability();
            temperature += data[i].getTemperature();
            apparentTemperature += data[i].getApparentTemperature();
            dewPoint += data[i].getDewPoint();
            humidity += data[i].getHumidity();
            windSpeed += data[i].getWindSpeed();
            windGust += data[i].getWindGust();
            windBearing += data[i].getWindBearing();
            cloudCover += data[i].getCloudCover();
            pressure += data[i].getPressure();
            ozone += data[i].getOzone();
            uvIndex += data[i].getUvIndex();

            count++;
        }

        HourlyAverage hourlyAverage = new HourlyAverage();

        hourlyAverage.setPrecipIntensity(precipIntensity/count);
        hourlyAverage.setPrecipProbability(precipProbability/count);
        hourlyAverage.setTemperature(temperature/count);
        hourlyAverage.setApparentTemperature(apparentTemperature/count);
        hourlyAverage.setDewPoint(dewPoint/count);
        hourlyAverage.setHumidity(humidity/count);
        hourlyAverage.setWindSpeed(windSpeed/count);
        hourlyAverage.setWindGust(windGust/count);
        hourlyAverage.setWindBearing(windBearing/count);
        hourlyAverage.setCloudCover(cloudCover/count);
        hourlyAverage.setPressure(pressure/count);
        hourlyAverage.setOzone(ozone/count);
        hourlyAverage.setUvIndex(uvIndex/count);

        return hourlyAverage;
    }



    public Forecast getHistoricalWeather(double latitude, double longitude, String time) {

        long dateInSecs = DateUnix.dateToSeconds(time);

        Forecast historicalWeather = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude+","+ dateInSecs, Forecast.class);
        return historicalWeather;
    }

    public ArrayList<HistoricalDaySummary> getHistoricalSummary(double latitude, double longitude, String time) {

        long dateInSecs = DateUnix.dateToSeconds(time);

        ArrayList<HistoricalDaySummary> responses = new ArrayList<>();

        for (long i = dateInSecs; i<1_483_228_800L; i+=31_556_952L) {
            Forecast historicalWeather = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                    "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude+","+ i, Forecast.class);

            HistoricalDaySummary historicalDaySummary = new HistoricalDaySummary(historicalWeather);
            responses.add(historicalDaySummary);
        }

        return responses;
    }


    public ArrayList<WeeklyForecast> getWeeklyForecast(double latitude, double longitude) {
        Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);

        ArrayList<WeeklyForecast> weeklyForecast = new ArrayList<>();

        WeeklyForecast weeklyForecastDay;

        for (int i = 0; i<forecast.getDaily().getData().length; i++) {
            weeklyForecastDay = new WeeklyForecast(forecast.getDaily().getData()[i] );
            weeklyForecast.add(weeklyForecastDay);
        }

        return weeklyForecast;
    }


    public ArrayList<WeeklyForecast> getDBWeeklyForecast(){
        return weatherMapper.getDBWeeklyForecast();
    }







    public ArrayList<WeeklyForecast> addNewWeeklyForecast (double latitude, double longitude) {

        //create a date formatter
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy;");

        //create a calendar object that will receive the date of the last row in the weather
        //database
        Calendar dateOfLastRowInDB = Calendar.getInstance();

        //if database is empty can throw an ArrayIndexOutOfBoundsException
        try {
            //string that holds the date of the last row in the database
            ArrayList<WeeklyForecast> weeklyForecastArrayList = weatherMapper.getDBWeeklyForecast();
            String dateInText = weeklyForecastArrayList.get( getDBWeeklyForecast().size()-1 ).getDate();

            //convert date of last row in database from String to Calendar
            try {
                dateOfLastRowInDB.setTime(df.parse(dateInText) );
            } catch (ParseException e) {
                e.printStackTrace();
            }

        //if database is empty, populate it with the next week's forecast from the API call
        } catch (ArrayIndexOutOfBoundsException exc) {
            Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                    "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);

            //object to receive each daily forecast in next week in the for loop
            WeeklyForecast weeklyForecast;

            for (int i=0; i<8; i++) {
                weeklyForecast = new WeeklyForecast(forecast.getDaily().getData()[i] );
                weatherMapper.insertDBWeeklyForecast(weeklyForecast);
            }
            return weatherMapper.getDBWeeklyForecast();
        }


        //Calendar object that holds the date 8 days ahead
        Calendar nextWeekDate = Calendar.getInstance();
        nextWeekDate.add(Calendar.DATE,8);


        //if database does not have the whole forecast for the week ahead, populate it with the remaining
        //daily forecasts; else, return weekly forecast from database instead of from API call
        if (dateOfLastRowInDB.get(Calendar.DATE) < nextWeekDate.get(Calendar.DATE) ) {

            Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                    "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);


            //TODO: current implementation works only if program is run every day


            WeeklyForecast weeklyForecast;

//            long diffBetweenDatesInSecs = nextWeekDate.getTimeInMillis()/1000 - dateOfLastRowInDB.getTimeInMillis()/1000;
//            int diffBetweenDatesInDays = DateUnix.diffBetweenDatesInDays(diffBetweenDatesInSecs);

            for (int i = dateOfLastRowInDB.get(Calendar.DATE); i < nextWeekDate.get(Calendar.DATE); i++) {
                weeklyForecast = new WeeklyForecast(forecast.getDaily().getData()[i] );
                weatherMapper.insertDBWeeklyForecast(weeklyForecast);
            }
            return weatherMapper.getDBWeeklyForecast();

        } else {
            return weatherMapper.getDBWeeklyForecast();
        }
    }


}
