public class ForestFireGUI {

    private ForestField forestField;

    public ForestFireGUI(ForestField forestField) {
        this.forestField = forestField;
    }

    public void launch() {
        // launch GUI

        // wait for "play" button

        boolean fireStillBuring = true;
        while (fireStillBuring) {
            fireStillBuring = forestField.spreadFireStep()
            // update GUI according to forestField data
            
            // wait for a while
        }
    }
}