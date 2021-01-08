package breakout.model;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 * A Paddle for the Breakout game
 *
 */
public class Paddle implements IPositionable{

    public static final double PADDLE_WIDTH = 60;  // Default values, use in constructors, not directly
    public static final double PADDLE_HEIGHT = 10;
    public static final double PADDLE_SPEED = 3;

    private double xPosition;
    private double yPosition;
    private double velocity;


    public Paddle(double xPosition, double yPosition, double velocity){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.velocity = velocity;
    }

    public Paddle(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.velocity = 0;
    }

    public void move(){
        if(0 <= getX() && getMaxX() <= GAME_WIDTH)
            xPosition += velocity;
        if(0 > getX())
            xPosition = 0;
        if(getMaxX() > GAME_WIDTH)
            xPosition = GAME_WIDTH - PADDLE_WIDTH;
    }

    public void setVelocity(double velocity){
        this.velocity = velocity;
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
        return PADDLE_WIDTH;
    }

    public double getMaxX() {
        return xPosition + PADDLE_WIDTH;
    }

    public double getMaxY() {
        return yPosition + PADDLE_HEIGHT;
    }

    @Override
    public double getHeight() {
        return PADDLE_HEIGHT;
    }
}
