package breakout.model;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 * A Paddle for the Breakout game
 *
 */
public class Paddle extends AbstractMoveable {

    public static final double PADDLE_WIDTH = 60;  // Default values, use in constructors, not directly
    public static final double PADDLE_HEIGHT = 10;
    public static final double PADDLE_SPEED = 3;


    public Paddle(double xPosition, double yPosition){
        super(xPosition, yPosition, PADDLE_WIDTH, PADDLE_HEIGHT, 0, 0);
    }


    public void move(){
        if(0 <= getX() && getMaxX() <= GAME_WIDTH)
            xPosition += getxVelocity();
        if(0 > getX())
            xPosition = 0;
        if(getMaxX() > GAME_WIDTH)
            xPosition = GAME_WIDTH - PADDLE_WIDTH;
    }
}
