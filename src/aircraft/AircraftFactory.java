package aircraft;

import models.Coordinates;

public class AircraftFactory {
    private static AircraftFactory instance = null;
    
    private AircraftFactory() {
        // Private constructor for Singleton pattern
    }
    
    public static AircraftFactory getInstance() {
        if (instance == null) {
            instance = new AircraftFactory();
        }
        return instance;
    }
    
    public Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinates) {
        Flyable aircraft = null;
        
        switch (p_type.toLowerCase()) {
            case "helicopter":
                aircraft = new Helicopter(Aircraft.nextId(), p_name, p_coordinates);
                break;
            case "jetplane":
                aircraft = new JetPlane(Aircraft.nextId(), p_name, p_coordinates);
                break;
            case "baloon":
                aircraft = new Baloon(Aircraft.nextId(), p_name, p_coordinates);
                break;
            default:
                System.out.println("Unknown aircraft type: " + p_type);
                break;
        }
        
        return aircraft;
    }
}