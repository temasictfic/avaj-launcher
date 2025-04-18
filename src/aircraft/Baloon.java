package aircraft;

import java.io.FileWriter;
import java.io.IOException;

import models.Coordinates;

public class Baloon extends Aircraft {
    public Baloon(long p_id, String p_name, Coordinates p_coordinates) {
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
                longitude += 2;
                height = Math.min(height + 4, 100);
                logMessage("Let's enjoy the good weather and take some pics.");
                break;
            case "RAIN":
                height = Math.max(height - 5, 0);
                logMessage("Damn you rain! You messed up my baloon.");
                break;
            case "FOG":
                height = Math.max(height - 3, 0);
                logMessage("Can't see anything in this fog. Hope we don't hit a mountain!");
                break;
            case "SNOW":
                height = Math.max(height - 15, 0);
                logMessage("It's snowing. We're gonna crash.");
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
            fw.write("Baloon#" + this.name + "(" + this.id + "): " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to simulation.txt: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "Baloon#" + this.name + "(" + this.id + ")";
    }
}