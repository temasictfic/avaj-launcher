package simulator;

import aircraft.AircraftFactory;
import aircraft.Flyable;
import models.Coordinates;
import weather.WeatherTower;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Simulator {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new SimulatorException("Usage: java simulator.Simulator <scenario_file>");
            }
            
            // Reset simulation.txt file
            File simulationFile = new File("simulation.txt");
            simulationFile.delete();
            simulationFile.createNewFile();
            
            // Read scenario file
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line = reader.readLine();
            
            if (line == null) {
                reader.close();
                throw new SimulatorException("Error: Empty scenario file");
            }
            
            // Parse simulation cycles
            int simulationCycles;
            try {
                simulationCycles = Integer.parseInt(line.trim());
                if (simulationCycles <= 0) {
                    reader.close();
                    throw new SimulatorException("Error: Number of simulation cycles must be positive");
                }
            } catch (NumberFormatException e) {
                reader.close();
                throw new SimulatorException("Error: First line must be a positive integer");
            }
            
            // Initialize tower and factory
            WeatherTower weatherTower = new WeatherTower();
            AircraftFactory factory = AircraftFactory.getInstance();
            
            // Parse aircraft data
            line = reader.readLine();
            int lineCount = 2; // First line is simulation cycles, so we start at line 2
            
            while (line != null) {
                try {
                    String[] parts = line.trim().split("\\s+");
                    
                    if (parts.length != 5) {
                        reader.close();
                        throw new SimulatorException("Error: Invalid aircraft specification at line " + lineCount);
                    }
                    
                    String type = parts[0];
                    String name = parts[1];
                    int longitude = Integer.parseInt(parts[2]);
                    int latitude = Integer.parseInt(parts[3]);
                    int height = Integer.parseInt(parts[4]);
                    
                    if (longitude < 0 || latitude < 0 || height < 0) {
                        reader.close();
                        throw new SimulatorException("Error: Coordinates must be positive at line " + lineCount);
                    }
                    
                    Coordinates coordinates = new Coordinates(longitude, latitude, height);
                    Flyable aircraft = factory.newAircraft(type, name, coordinates);
                    
                    if (aircraft == null) {
                        reader.close();
                        throw new SimulatorException("Error: Invalid aircraft type at line " + lineCount);
                    }
                    
                    aircraft.registerTower(weatherTower);
                } catch (NumberFormatException e) {
                    reader.close();
                    throw new SimulatorException("Error: Invalid coordinates at line " + lineCount);
                }
                
                line = reader.readLine();
                lineCount++;
            }
            
            reader.close();
            
            // Run simulation
            for (int i = 0; i < simulationCycles; i++) {
                weatherTower.changeWeather();
            }
            
        } catch (SimulatorException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}