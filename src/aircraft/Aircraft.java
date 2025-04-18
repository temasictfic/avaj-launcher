package aircraft;

import models.Coordinates;

public class Aircraft extends Flyable {
    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private static long idCounter = 1;
    
    protected Aircraft(long p_id, String p_name, Coordinates p_coordinates) {
        this.id = p_id;
        this.name = p_name;
        this.coordinates = p_coordinates;
    }
    
    public void updateConditions() {
        // This method will be overridden by subclasses
    }
    
    protected static long nextId() {
        return idCounter++;
    }
}
