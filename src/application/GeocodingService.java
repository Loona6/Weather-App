package application;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeocodingService {
    private final String apiKey;
    private final HttpClient client;
    private final Gson gson;

    public GeocodingService(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public double[] getLatLon(String city) throws Exception {
        // OpenCage API URL
        String url = "https://api.opencagedata.com/geocode/v1/json?q=" + 
                     city.replace(" ", "%20") + "&key=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        OpenCageResponse res = gson.fromJson(response.body(), OpenCageResponse.class);

        if(res.results != null && res.results.length > 0) {
            return new double[]{res.results[0].geometry.lat, res.results[0].geometry.lng};
        }
        return null;
    }

    private static class OpenCageResponse {
        OpenCageResult[] results;
    }

    private static class OpenCageResult {
        Geometry geometry;
    }

    private static class Geometry {
        double lat;
        double lng;
    }
}
