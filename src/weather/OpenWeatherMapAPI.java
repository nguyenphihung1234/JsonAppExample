package weather;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMapAPI {

    private static final String API_KEY = "cf8a0a169d2bd6b222a95bfa0b617a8e"; // Thay bằng API key của bạn
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        String cityName = "Ho Chi Minh";
        getCurrentWeather(cityName);
    }

    public static void getCurrentWeather(String cityName) {
        try {
            // Tạo URL với các tham số truy vấn
            String urlString = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, cityName, API_KEY);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Phân tích JSON từ phản hồi
                JsonObject jsonResponse = (JsonObject) Jsoner.deserialize(response.toString(), new JsonObject());

                // Lấy mô tả thời tiết
                JsonArray weatherArray = (JsonArray) jsonResponse.get("weather");
                JsonObject weatherObject = (JsonObject) weatherArray.get(0);
                String weatherDescription = (String) weatherObject.get("description");

                // Lấy các thông tin khác
                JsonObject mainObject = (JsonObject) jsonResponse.get("main");
                double temp = ((Number) mainObject.get("temp")).doubleValue();
                int humidity = ((Number) mainObject.get("humidity")).intValue();

                JsonObject windObject = (JsonObject) jsonResponse.get("wind");
                double windSpeed = ((Number) windObject.get("speed")).doubleValue();

                // Hiển thị thông tin thời tiết
                System.out.println("Weather in " + cityName + ":");
                System.out.println("Description: " + weatherDescription);
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Wind Speed: " + windSpeed + " m/s");
            } else {
                System.out.println("Failed to get weather data: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
