package breakout.model;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
        A wall for the ball to bounce
 */


public class Wall implements IPositionable{

    public static final double WALL_THICKNESS = 10;
    private double width;
    private double height;
    private double xPosition;
    private double yPosition;
    private Dir dir;


    public Wall(double xPosition, double yPosition, double width, double height){
        this.xPosition = xPosition; // TODO set default positions?
        this.yPosition = yPosition;
        this.width = width; // If same as game size?
        this.height = height;
    }

    public Wall(double xPosition, double yPosition, Dir layout){
        this.xPosition = xPosition; // TODO set default positions?
        this.yPosition = yPosition;
        setWidthAndHeight(layout);
        this.dir = layout;
    }

    public Wall(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = GAME_WIDTH; // If same as game size?
        this.height = GAME_HEIGHT;
    }

    // Different depending on what type of wall
    private void setWidthAndHeight(Dir layout){
        if(layout == Dir.HORIZONTAL){
            width = GAME_WIDTH;
            height = WALL_THICKNESS;
        }
        else{
            width = WALL_THICKNESS;
            height = GAME_HEIGHT;
        }
    }


    public enum Dir {
        HORIZONTAL, VERTICAL_LEFT, VERTICAL_RIGHT;
    }

    public Dir getDir() {
        return dir;
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
    public double getMaxX() {
        return xPosition + width;
    }

    @Override
    public double getMaxY() {
        return yPosition + height;
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
