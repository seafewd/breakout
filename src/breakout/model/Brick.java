package breakout.model;

/*
 *   A brick for the rows of bricks
 */

public class Brick extends AbstractPositionable {

    public static final double BRICK_WIDTH = 20;  // Default values, use in constructors, not directly
    public static final double BRICK_HEIGHT = 10;

    private final int points;

    public Brick(double initXPos, double initYPos, int points){
        super(initXPos, initYPos, BRICK_WIDTH, BRICK_HEIGHT);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

}

