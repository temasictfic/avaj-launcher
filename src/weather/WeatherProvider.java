package weather;

import models.Coordinates;

public class WeatherProvider {
    private static WeatherProvider instance = null;
    private String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};
    
    private WeatherProvider() {
        // Private constructor for Singleton pattern
    }
    
    public static WeatherProvider getInstance() {
        if (instance == null) {
            instance = new WeatherProvider();
        }
        return instance;
    }
    
    public String getCurrentWeather(Coordinates p_coordinates) {
        // Generate weather based on coordinates
        int sum = p_coordinates.getLongitude() + p_coordinates.getLatitude() + p_coordinates.getHeight();
        return weather[sum % 4];
    }
}
