package application;

public class WeatherData {
    public Location location;
    public Current current;
    public Forecast forecast;

    public static class Location { public String name, country, localtime; }
    public static class Current { public double temp_c, feelslike_c, precip_mm; public int humidity, cloud; public Condition condition; }
    public static class Forecast { public ForecastDay[] forecastday; }
    public static class ForecastDay { public String date; public Day day; }
    public static class Day { public double maxtemp_c, mintemp_c; public Condition condition; }
    public static class Condition { public String text; }
}
