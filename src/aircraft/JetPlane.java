package aircraft;

import java.io.FileWriter;
import java.io.IOException;

import models.Coordinates;

public class JetPlane extends Aircraft {
    public JetPlane(long p_id, String p_name, Coordinates p_coordinates) {
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
                latitude += 10;
                height = Math.min(height + 2, 100);
                logMessage("Clear skies ahead! Time to put on some sunglasses.");
                break;
            case "RAIN":
                latitude += 5;
                logMessage("It's raining. Better watch out for lightings.");
                break;
            case "FOG":
                latitude += 1;
                logMessage("Visibility zero! Switching to instruments.");
                break;
            case "SNOW":
                height = Math.max(height - 7, 0);
                logMessage("OMG! Winter is coming!");
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
            fw.write("JetPlane#" + this.name + "(" + this.id + "): " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to simulation.txt: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "JetPlane#" + this.name + "(" + this.id + ")";
    }
}