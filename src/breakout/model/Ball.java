package breakout.model;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 *    A Ball for the Breakout game
 */

public class Ball extends AbstractMoveable {

    private static final double defaultyVelocity = -1.9;
    private static final double defaultxPosition = GAME_WIDTH/2;
    private static final double defaultyPosition = GAME_HEIGHT/2;
    private static final double DEFAULT_WIDTH = 15;
    private static final double DEFAULT_HEIGHT = 15;

    public Ball(double xPosition, double yPosition, double width, double height){
        super(xPosition, yPosition, width, height, Breakout.randomInteger(-2, 2), defaultyVelocity);
    }

    // Default velocity and radius
    public Ball(double xPosition, double yPosition){
        this(xPosition, yPosition, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    // Default values
    public Ball(){
        this(defaultxPosition, defaultyPosition, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void move(){
        xPosition += getxVelocity();
        yPosition += getyVelocity();
    }

}
