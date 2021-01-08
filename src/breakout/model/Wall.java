package breakout.model;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
        A wall for the ball to bounce
 */


public class Wall extends AbstractPositionable {

    public static final double WALL_THICKNESS = 10;
    private Dir dir;


    public Wall(double xPosition, double yPosition, double width, double height, Dir dir){
        super(xPosition, yPosition, width, height);
        this.dir = dir;
    }



    public enum Dir {
        HORIZONTAL, VERTICAL_LEFT, VERTICAL_RIGHT;
    }

    public Dir getDir() {
        return dir;
    }
}
