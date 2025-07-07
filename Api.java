import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPIClient {

    // Replace with your actual OpenWeatherMap API key
    private static final String API_KEY = "your_api_key_here";  
    private static final String CITY = "Hyderabad";

    public static void main(String[] args) {
        try {
            // URL of public weather API
            String urlString = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", 
                CITY, API_KEY
            );

            // Create URL object
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // HTTP GET request

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                // Read the response line by line
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extract weather details
                String cityName = jsonResponse.getString("name");
                JSONObject main = jsonResponse.getJSONObject("main");
                double temp = main.getDouble("temp");
                double feels_like = main.getDouble("feels_like");
                int humidity = main.getInt("humidity");

                JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");

                // Display data in structured format
                System.out.println("Weather Information:");
                System.out.println("--------------------");
                System.out.println("City       : " + cityName);
                System.out.println("Temperature: " + temp + " °C");
                System.out.println("Feels Like : " + feels_like + " °C");
                System.out.println("Humidity   : " + humidity + " %");
                System.out.println("Condition  : " + description);

            } else {
                System.out.println("HTTP request failed. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
