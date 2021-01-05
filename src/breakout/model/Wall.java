package breakout.model;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
        A wall for the ball to bounce
 */


public class Wall implements IPositionable{

    private double width;
    private double height;
    private double xPosition;
    private double yPosition;


    public Wall(double xPosition, double yPosition, double width, double height){
        this.xPosition = xPosition; // TODO set default positions?
        this.yPosition = yPosition;
        this.width = width; // If same as game size?
        this.height = height;
    }

    public Wall(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = GAME_WIDTH; // If same as game size?
        this.height = GAME_HEIGHT;
    }


    public enum Dir {
        HORIZONTAL, VERTICAL;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getMaxX() {
        return 0;
    }

    @Override
    public double getMaxY() {
        return 0;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
