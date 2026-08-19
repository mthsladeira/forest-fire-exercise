import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ForestFireEngine {

    private static ForestField forestField;
    private static ForestFireGUI forestGUI;

    public static void main(String[] args) {

        loadConfiguration("simulation.properties");
        
        forestGUI = new ForestFireGUI(forestField);
        forestGUI.launch();
    }

    public static void loadConfiguration(String filePath) {

        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(filePath)) {
            // Load the properties file
            prop.load(input);

            // 1. Parse simple parameters
            int h = Integer.parseInt(prop.getProperty("forest.height"));
            int l = Integer.parseInt(prop.getProperty("forest.length"));
            double p = Double.parseDouble(prop.getProperty("fire.spreadProbability"));

            System.out.println("Forest (l x h): " + l + " x " + h);
            System.out.println("Fire Spread Probability: " + p*100 + "%");

            // 2. Parse the initial fire positions
            String firesString = prop.getProperty("fire.initialPositions");
            List<Position> initialFires = parsePositions(firesString);

            System.out.println("Initial Fires:");
            for (Position pos : initialFires) {
                System.out.println(" - Fire at x: " + pos.getX() + ", y: " + pos.getY());
            }

            forestField = new ForestField(h, l, p, initialFires);

        } catch (IOException e) {
            System.err.println("Error reading the configuration file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number in the configuration file: " + e.getMessage());
        }
    }

    private static List<Position> parsePositions(String positionsString) {
        List<Position> positions = new ArrayList<>();

        // Check if the string is empty or null to avoid crashes
        if (positionsString == null || positionsString.trim().isEmpty()) {
            return positions;
        }

        // Split by semicolon to get each "x,y" pair
        String[] pairs = positionsString.split(";");

        for (String pair : pairs) {
            // Split by comma to separate x and y
            String[] coords = pair.split(",");

            if (coords.length == 2) {
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());
                positions.add(new Position(x, y));
            }
        }

        return positions;
    }
}
