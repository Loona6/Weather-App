package application;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeatherAPIService {
    private final String apiKey;
    private final HttpClient client;
    private final Gson gson;

    public WeatherAPIService(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public WeatherData getCurrentWeather(double lat, double lon) throws Exception {
        String url = "https://api.weatherapi.com/v1/current.json?key=" + apiKey
                + "&q=" + lat + "," + lon + "&aqi=yes";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), WeatherData.class);
    }

    public WeatherData getForecast(double lat, double lon, int days) throws Exception {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiKey
                + "&q=" + lat + "," + lon + "&days=" + days + "&aqi=yes&alerts=no";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), WeatherData.class);
    }

    public WeatherData[] getLastNDaysWeather(double lat, double lon, int n) throws Exception {
        WeatherData[] history = new WeatherData[n];
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for(int i = 0; i < n; i++) {
            LocalDate date = today.minusDays(i+1);
            String url = "https://api.weatherapi.com/v1/history.json?key=" + apiKey
                    + "&q=" + lat + "," + lon + "&dt=" + date.format(formatter);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            history[i] = gson.fromJson(response.body(), WeatherData.class);
        }
        return history;
    }
}
