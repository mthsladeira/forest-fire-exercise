public class ForestFireEngine {
    private ForestField forestField;
    private ForestFireGUI forestGUI;

    public static void main(String[] args) {

        // launch GUI to get input file path

        createForestFieldFromInputParameters(file);

        forestGUI = new ForestFireGUI(forestField);

    }

    private void createForestFieldFromInputParameters(Path inputFilePath) {

        int h = 0;
        int l = 0;
        double p = 0;
        List<Position> listStartingFires = new ArrayList<Position>;

        // Read inputs from file

        forestField = new ForestField(h, l, listStartingFires);

    }
}