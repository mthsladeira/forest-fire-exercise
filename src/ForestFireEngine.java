import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ForestFireEngine {

    public static void main(String[] args) {

        // launch GUI to get input file path
        // dummyPath
        Path file = new File("dummyPath").toPath();

        ForestField forestField;
        try {
            forestField = createForestFieldFromInputParameters(file);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        ForestFireGUI forestGUI = new ForestFireGUI(forestField);
        forestGUI.launch();

    }

    private static ForestField createForestFieldFromInputParameters(Path inputFilePath) {

        int h = 0;
        int l = 0;
        double p = 0;
        List<ForestField.Position> listStartingFires = new ArrayList<ForestField.Position>();

        // Read inputs from file

        ForestField forestField = new ForestField(h, l, p, listStartingFires);
        return forestField;
    }
}