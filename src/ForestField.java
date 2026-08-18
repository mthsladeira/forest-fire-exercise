public class ForestField {

    private int h;
    private int l;

    private int p;

    public class Position {
        private int x, y;
        public Position(int x, int y) {
            this.x=x;
            this.y=y;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

    private List<Position> startingFirePositions;

    private int[][] forestFieldSlots;

    private List<Position> currentFirePositions;
    private List<FireSpreadPos> listFireSpread;

    private class FireSpreadPos {
        private Position pos;
        private double p;
        public FireSpreadPos(int x, int y, double p) {
            this.pos = new Position(x, y);
            if (p < 0 || p > 1) throw new IllegalArgumentException("Probability P must be between 0 and 1");
            this.p = p;
        }
        public Position getPos(){
            return pos;
        }
        public double getP() {
            return p;
        }
        // transforms p into the probability of the union of independent events p and q
        public void increaseProbability(double q) {
            // p = 1 - (1-p)*(1-q);
            p = p + q - p*q;
        }
    }

    public ForestField(int height, int length, int probabilityFireSpread, List<Position> startingFirePositions) {
        
        if (h <= 0) throw new IllegalArgumentException("Forest field height has to be larger than zero");
        this.h = height;

        if (l <= 0) throw new IllegalArgumentException("Forest field length has to be larger than zero");
        this.l = length;

        if (p < 0 || p > 1) throw new IllegalArgumentException("Fire spread probability must be a value between zero and one");
        this.p = probabilityFireSpread;

        for (Position pos : startingFirePositions) {
            int x = pos.getX();
            int y = pos.getY();
            if (x <= 0 || x > l) throw new IllegalArgumentException("Invalid position: X coordinate must be between 1 and the field length L");
            if (y <= 0 || y > h) throw new IllegalArgumentException("Invalid position: Y coordinate must be between 1 and the field height H");
        }
        this.startingFirePositions = new ArrayList<Position>(startingFirePositions);

        this.forestFieldSlots = new int[l][h];
        for (Position pos : this.startingFirePositions) {
            int x = pos.getX();
            int y = pos.getY();
            this.forestFieldSlots[x-1][y-1] = 1;
        }

        this.currentFirePositions = new ArrayList<Position>(startingFirePositions);
    }

    public int getH() {
        return h;
    }
    public int getL() {
        return l;
    }
    public double getP() {
        return p;
    }
    public List<Position> getStartingFirePos() {
        return new ArrayList<Position>(startingFirePositions);
    }
    public int getForestFieldSlot(int x, int y) {
        return forestFieldSlots[x-1][y-1];
    }

    public boolean spreadFireStep() {
        if (currentFirePositions.isEmpty()) {
            return false;
        }

        listFireSpread = new ArrayList<FireSpreadPos>();

        // create list of possible new fires
        for (Position pos : currentFirePositions) {
            spreadSingleFireSpot(listFireSpread, pos);
        }

        // decide on probabilities if new fires catch
        List<Position> listNewFires = new ArrayList<Position>();
        for (FireSpreadPos fireSpreadPos : listFireSpread) {
            double random01 = Math.random();
            if (random01 < fireSpreadPos.getP()) {
                listNewFires.add(fireSpreadPos.getPos());
            }
        }

        // fire into ash
        for (Position pos : currentFirePositions) {
            int x = pos.getX();
            int y = pos.getY();
            // expected current value = 1
            forestField[x-1][y-1] = 2;
        }

        // new fires
        for (Position pos : listNewFires) {
            int x = pos.getX();
            int y = pos.getY();
            // expected current value = 0
            forestField[x-1][y-1] = 1;
        }

        currentFirePositions = new ArrayList<Position>(listNewFires);

        // raise flag for GUI?

        return true;
    }

    private void spreadSingleFireSpot(List<FireSpreadPos> theListFireSpread, Position position) {
        int x = position.getX();
        int y = position.getY();

        int xEast = x+1;
        if (xEast <= l) {
            addFireSpot(theListFireSpread, xEast, y);
        }

        int xWest = x-1;
        if (xWest > 0) {
            addFireSpot(theListFireSpread, xWest, y);
        }

        int yNorth = y+1;
        if (yNorth <= h) {
            addFireSpot(theListFireSpread, x, yNorth);
        }

        int ySouth = y-1;
        if (ySouth > 0) {
            addFireSpot(theListFireSpread, x, ySouth);
        }
    }

    private void addFireSpot(List<FireSpreadPos> theListFireSpread, int x, int y) {
       
        // Slot on ashes or already on fire: nothing to do
        if (forestFieldSlots[x-1][y-1] != 0) {
            return;
        }

        // check if another fire spot has already tried to spread fire to this spot
        FireSpreadPos samePosition;
        for (FireSpreadPos firePos : theListFireSpread) {
            Position pos = firePos.getPos();
            int xPos = pos.getX();
            int yPos = pos.getY();
            if (x == xPos && y == yPos) {
                samePosition = firePos;
            }
        }

        // no other fire spot has tried to spread its fire here
        if (samePosition == null) {
            FireSpreadPos newFireSpread = new FireSpreadPos(x, y, p)
            theListFireSpread.add()
        }
        
        // another fire spot has tried to spread its fire here: probabilities must combine
        else {
            samePosition.increaseProbability(p);
        }
    }

}