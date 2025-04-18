package aircraft;

import java.io.FileWriter;
import java.io.IOException;

import models.Coordinates;

public class Helicopter extends Aircraft {
    public Helicopter(long p_id, String p_name, Coordinates p_coordinates) {
        super(p_id, p_name, p_coordinates);
    }
    
    @Override
    public void updateConditions() {
        String weather = weatherTower.getWeather(this.coordinates);
        int longitude = coordinates.getLongitude();
        int latitude = coordinates.getLatitude();
        int height = coordinates.getHeight();
        
        switch (weather) {
            case "SUN":
                longitude += 10;
                height = Math.min(height + 2, 100);
                logMessage("This is hot.");
                break;
            case "RAIN":
                longitude += 5;
                logMessage("It's raining. Better call Saul!");
                break;
            case "FOG":
                longitude += 1;
                logMessage("These blades can't cut through this fog!");
                break;
            case "SNOW":
                height = Math.max(height - 12, 0);
                logMessage("My rotor is going to freeze!");
                break;
        }
        
        this.coordinates = new Coordinates(longitude, latitude, height);
        
        if (coordinates.getHeight() == 0) {
            logMessage("landing.");
            weatherTower.unregister(this);
        }
    }
    
    private void logMessage(String message) {
        try (FileWriter fw = new FileWriter("simulation.txt", true)) {
            fw.write("Helicopter#" + this.name + "(" + this.id + "): " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to simulation.txt: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "Helicopter#" + this.name + "(" + this.id + ")";
    }
}
