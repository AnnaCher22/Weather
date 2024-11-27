package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class Weather {
    public static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    static String apiKey = "650ffd9a-cef2-4f45-aa3d-4943e04936fa";
    private final double latitude;
    private final double longitude;

    public Weather(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void fetchAndProcessWeatherData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(API_URL + "?lat=" + latitude + "&lon=" + longitude);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("X-Yandex-Weather-Key", apiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body();
            System.out.println("Полный ответ:");
            System.out.println(body);

            // Разбор JSON вручную (можно использовать библиотеку Jackson или Gson)
            int startIndex = body.indexOf("\"temp\":") + "\"temp\":".length();
            int endIndex = body.indexOf(",", startIndex);
            String tempStr = body.substring(startIndex, endIndex).trim();
            float currentTemp = Float.parseFloat(tempStr);
            System.out.printf("\nТекущая температура: %.1f°C\n", currentTemp);

            List<Float> temperatures = new ArrayList<>();
            while (body.contains("\"temp\":")) {
                startIndex = body.indexOf("\"temp\":") + "\"temp\":".length();
                endIndex = body.indexOf(",", startIndex);
                tempStr = body.substring(startIndex, endIndex).trim();
                temperatures.add(Float.parseFloat(tempStr));
                body = body.substring(endIndex);
            }

            float averageTemp = calculateAverageTemperature(temperatures);
            System.out.printf("\nСредняя температура за последний период: %.1f°C\n", averageTemp);
        } else {
            System.err.println("Произошла ошибка при получении данных. Код ошибки: " + response.statusCode());
        }
    }

    private float calculateAverageTemperature(List<Float> temperatures) {
        float sum = 0;
        for (float temp : temperatures) {
            sum += temp;
        }
        return sum / temperatures.size();
    }
}