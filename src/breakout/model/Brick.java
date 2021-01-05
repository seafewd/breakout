package breakout.model;

/*
 *   A brick for the rows of bricks
 */

public class Brick implements IPositionable{

    public static final double BRICK_WIDTH = 20;  // Default values, use in constructors, not directly
    public static final double BRICK_HEIGHT = 10;

    private double xPosition;
    private double yPosition;
    private int points;

    public Brick(double initXPos, double initYPos, int points){
        xPosition = initXPos;
        yPosition = initYPos;
        this.points = points;
    }

    @Override
    public double getX() {
        return xPosition;
    }

    @Override
    public double getY() {
        return yPosition;
    }

    @Override
    public double getWidth() {
        return BRICK_WIDTH;
    }

    @Override
    public double getHeight() {
        return BRICK_HEIGHT;
    }

    public int getPoints(){
        return points;
    }

    public double getMaxX() {
        return xPosition + BRICK_WIDTH;
    }

    public double getMaxY() {
        return yPosition + BRICK_HEIGHT;
    }
}

