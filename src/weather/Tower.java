package weather;

import aircraft.Flyable;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Tower {
    private List<Flyable> observers = new ArrayList<>();
    private List<Flyable> toRemove = new ArrayList<>();
    
    public void register(Flyable p_flyable) {
        if (!observers.contains(p_flyable)) {
            observers.add(p_flyable);
            logMessage("Tower says: " + p_flyable + " registered to weather tower.");
        }
    }
    
    public void unregister(Flyable p_flyable) {
        toRemove.add(p_flyable);
        logMessage("Tower says: " + p_flyable + " unregistered from weather tower.");
    }
    
    protected void conditionChanged() {
        for (Flyable flyable : observers) {
            flyable.updateConditions();
        }
        
        observers.removeAll(toRemove);
        toRemove.clear();
    }
    
    protected void logMessage(String message) {
        try (FileWriter fw = new FileWriter("simulation.txt", true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to simulation.txt: " + e.getMessage());
        }
    }
}
