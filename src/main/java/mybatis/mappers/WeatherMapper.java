package mybatis.mappers;

import mybatis.model.DarkSkyWeather.WeeklyForecast;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * Created by tanerali on 27/07/2017.
 */
@Mapper
public interface WeatherMapper {

    String GET_WEEKLY_FORECAST= "SELECT * FROM `weatherForecast`.weeklyForecast";

    String GET_SPECIFIC_DAY = "SELECT * FROM `weatherForecast`.weeklyForecast WHERE date = #{date}";

    String INSERT_WEEKLY_FORECAST = "INSERT INTO `weatherForecast`.weeklyForecast " +
            "(date, summary, sunrise, sunset, precipProbability, temperatureMax, windSpeed) " +
            "VALUES (#{date}, #{summary}, #{sunrise}, #{sunset}, " +
            "#{precipProbability}, #{temperatureMax}, #{windSpeed})";

    @Select(GET_WEEKLY_FORECAST)
    public ArrayList<WeeklyForecast> getDBWeeklyForecast();

    @Select(GET_SPECIFIC_DAY)
    public ArrayList<WeeklyForecast> getSpecificDay();

    @Insert(INSERT_WEEKLY_FORECAST)
    public int insertDBWeeklyForecast (WeeklyForecast weeklyForecast);
}
