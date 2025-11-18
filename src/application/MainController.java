package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.concurrent.Task;

public class MainController {

    @FXML private TextField cityInput;
    @FXML private VBox currentWeatherCard;
    @FXML private HBox forecastCardContainer; 
    @FXML private Label cityLabel, tempLabel, feelsLabel, humidityLabel, cloudLabel, precipLabel, timeLabel;
    @FXML private Button searchButton, btn3Day, btn7Day;
    @FXML private ToggleButton themeToggle;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private ImageView weatherIcon;

    private WeatherAPIService weatherService;
    private GeocodingService geoService;

    public void initialize() {
        String weatherApiKey = "5215afbd0d0149448bf174341252008";
        String geoApiKey = "3a263a933e504cb3a68d623175cdccf3";

        weatherService = new WeatherAPIService(weatherApiKey);
        geoService = new GeocodingService(geoApiKey);

        progressIndicator.setVisible(false);

        searchButton.setOnAction(e -> fetchWeather());
        btn3Day.setOnAction(e -> fetchForecast(3));
        btn7Day.setOnAction(e -> fetchForecast(7));
        themeToggle.setOnAction(e -> switchTheme());
    }

    private void fetchWeather() {
        String city = cityInput.getText().trim();
        if(city.isEmpty()) { showAlert("Please enter a city"); return; }
        progressIndicator.setVisible(true);

        Task<Void> task = new Task<>() {
            WeatherData data;
            double[] latLon;

            @Override
            protected Void call() throws Exception {
                latLon = geoService.getLatLon(city);
                if(latLon == null) throw new Exception("City not found");
                data = weatherService.getCurrentWeather(latLon[0], latLon[1]);
                return null;
            }

            @Override
            protected void succeeded() {
                cityLabel.setText(data.location.name + ", " + data.location.country);
                tempLabel.setText(data.current.temp_c + " °C");
                feelsLabel.setText("Feels like: " + data.current.feelslike_c + " °C");
                humidityLabel.setText("Humidity: " + data.current.humidity + "%");
                cloudLabel.setText("Cloud: " + data.current.cloud + "%");
                precipLabel.setText("Precip: " + data.current.precip_mm + " mm");
                timeLabel.setText("Local time: " + data.location.localtime);
                weatherIcon.setImage(selectIcon(data.current.condition.text));
                progressIndicator.setVisible(false);
            }

            @Override
            protected void failed() {
                showAlert("Failed to fetch weather data.");
                progressIndicator.setVisible(false);
            }
        };
        new Thread(task).start();
    }

    private void fetchForecast(int days) {
        String city = cityInput.getText().trim();
        if(city.isEmpty()) { showAlert("Enter a city"); return; }
        progressIndicator.setVisible(true);
        forecastCardContainer.getChildren().clear();

        Task<Void> task = new Task<>() {
            WeatherData data;
            double[] latLon;

            @Override
            protected Void call() throws Exception {
                latLon = geoService.getLatLon(city);
                if(latLon == null) throw new Exception("City not found");
                data = weatherService.getForecast(latLon[0], latLon[1], days);
                return null;
            }

            @Override
            protected void succeeded() {
                for(WeatherData.ForecastDay fd : data.forecast.forecastday) {
                    VBox card = new VBox(5);
                    card.getStyleClass().add("forecast-card");
                    card.setMinWidth(140);
                    card.setMaxWidth(140);
                    card.setPrefWidth(140);

                    Label date = new Label(fd.date);
                    date.getStyleClass().add("forecast-date");

                    Label cond = new Label(fd.day.condition.text);
                    cond.getStyleClass().add("forecast-condition");

                    Label temp = new Label(fd.day.mintemp_c + "°C - " + fd.day.maxtemp_c + "°C");
                    temp.getStyleClass().add("forecast-temp");

                    card.getChildren().addAll(date, cond, temp);
                    forecastCardContainer.getChildren().add(card);
                }
                progressIndicator.setVisible(false);
            }

            @Override
            protected void failed() {
                showAlert("Failed to fetch forecast data.");
                progressIndicator.setVisible(false);
            }
        };
        new Thread(task).start();
    }

    private Image selectIcon(String condition) {
        String lower = condition.toLowerCase();
        if(lower.contains("sun") || lower.contains("clear")) return new Image(getClass().getResource("/images/sunny.png").toExternalForm());
        if(lower.contains("rain") || lower.contains("drizzle")) return new Image(getClass().getResource("/images/rainy.png").toExternalForm());
        if(lower.contains("cloud")) return new Image(getClass().getResource("/images/cloudy.png").toExternalForm());
        if(lower.contains("snow")) return new Image(getClass().getResource("/images/snow.png").toExternalForm());
        return new Image(getClass().getResource("/images/sunny.png").toExternalForm());
    }

    private void switchTheme() {
        Scene scene = cityInput.getScene();
        if(themeToggle.isSelected()) {
            ThemeManager.applyDark(scene);
            themeToggle.setText("Light Mode");
        } else {
            ThemeManager.applyLight(scene);
            themeToggle.setText("Dark Mode");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
