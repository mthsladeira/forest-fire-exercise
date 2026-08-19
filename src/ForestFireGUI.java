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

            // update GUI according to forestField data
            printForestField();
            
            fireStillBuring = forestField.spreadFireStep();

            // wait for a while
            try {
                Thread.sleep(1000); // 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printForestField() {
        for (int y = forestField.getH(); y > 0; y--) {
            for (int x = 1; x <= forestField.getL(); x++) {
                int state = forestField.getForestFieldSlot(x, y);
                if (state == 0) {
                    System.out.print("o");
                } else if (state == 1) {
                    System.out.print("ˆ");
                } else if (state == 2) {
                    System.out.print(".");
                } else {
                    System.out.print("?");
                }
            }
            System.out.println();
        }
        System.out.println("-----");
    }
}