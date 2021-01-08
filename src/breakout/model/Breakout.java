package breakout.model;


import breakout.event.EventBus;
import breakout.event.ModelEvent;

import java.util.*;

/*
 *  Overall all logic for the Breakout Game
 *  Model class representing the full game
 *  This class should use other objects delegate work.
 *
 *  NOTE: Nothing visual here
 *
 */
public class Breakout {

    public static final double GAME_WIDTH = 400;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.05; // Increase ball speed
    public static final long SEC = 1_000_000_000;  // Nano seconds used by JavaFX

    private Ball ball;
    private Paddle paddle;
    private List<Wall> walls;
    private List<Brick> bricks;

    private List<Brick> toRemove = new ArrayList<>();

    private int nBricks = 20;
    private int nBalls = 5;
    int points;

    enum Direction {UP, DOWN, RIGHT, LEFT, NOTHING}


    public Breakout(Ball ball, Paddle paddle, List<Brick> bricks, List<Wall> walls){
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.walls = walls;
    }

    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {
        ball.move();
        paddle.move();

        doWallCollision();
        doBallPaddleCollision();
        doBallBrickCollision();

        // check if ball out of y position bounds
        checkBallOut();
    }


    /**
     * Generate random int
     * @param min
     * @param max
     * @return
     */

    // Used for functional decomposition
    public static int randomInteger(int min, int max) {
        Random gen = new Random(System.currentTimeMillis());
        return gen.nextInt((max - min) + 1) + min;
    }

    /**
     * Handles when ball falls out, calls event lose life or game over
     */

    private void checkBallOut(){
        if (ball.getY() >= GAME_HEIGHT) {
            if (getnBalls() != 0) {
                ball = new Ball();
                nBalls--;
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.LOSE_LIFE, "life lost"));
            } else {
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GAME_OVER, "game over"));
            }
        }
    }


    /**
     * Flips direction of ball if it reaches the edges (top, left and right)
     */

    // ----- Helper methods--------------
    private void doWallCollision(){
        for (Wall w:walls) {
            if(intersects(w) && w.getDir() == Wall.Dir.HORIZONTAL) {
                ball.setyVelocity(Math.abs(ball.getyVelocity()));
            }
            else if(intersects(w) && w.getDir() == Wall.Dir.VERTICAL_LEFT) {
                ball.setxVelocity(Math.abs(ball.getxVelocity()));
            }
            else if(intersects(w) && w.getDir() == Wall.Dir.VERTICAL_RIGHT) {
                ball.setxVelocity(-Math.abs(ball.getxVelocity()));
            }
        }
    }


    /**
     * Handles the brick collision. Eg. adds points, switches direction of ball and removes brick
     *
     * Checks for intersection and looks for the direction in which to send the ball, then changes direction
     * of ball upon impact.
     *
     * -- (Could maybe be broken up into smaller methods)
     */

    private void doBallBrickCollision(){
        for (Brick b : bricks) {
            if(intersects(b) && getDirection(b) == Direction.UP){
                ball.setyVelocity(-Math.abs(ball.getyVelocity()));
            }
            else if(intersects(b) && getDirection(b) == Direction.DOWN){
                ball.setyVelocity(Math.abs(ball.getyVelocity()));
            }
            else if(intersects(b) && getDirection(b) == Direction.LEFT){
                ball.setxVelocity(-Math.abs(ball.getxVelocity()));
            }
            else if(intersects(b) && getDirection(b) == Direction.RIGHT){
                ball.setxVelocity(Math.abs(ball.getxVelocity()));
            }

            if (intersects(b)) {
                points += b.getPoints();
                toRemove.add(b);
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK, ""));
            }
        }
        bricks.removeAll(toRemove);
        toRemove.clear();
    }

    /**
     * Handles collision with paddle.
     *
     * Switches direction of ball -- If ball moves beyond half the height (size) of the paddle then it will move past
     * and not bounce.
     *
     * Also trigger sounds
     */

    private void doBallPaddleCollision(){
        if(intersects(paddle) && ball.getMaxY() < paddle.getY() + paddle.getHeight()/2) {
            ball.setyVelocity(-Math.abs(ball.getyVelocity()));
            setBounceDirection(ball, paddle);
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE, ""));
        }
    }

    /**
     * Set the direction of the ball when it bounces off the paddle
     * @param ball ball
     * @param paddle paddle
     */
    private void setBounceDirection(Ball ball, Paddle paddle) {

        double paddleLPos = paddle.getX();
        double ballLPos = ball.getX() + ball.getWidth() / 2;

        double first = paddleLPos + paddle.getWidth()*.25;
        double second = paddleLPos + paddle.getWidth()*.50;
        double third = paddleLPos + paddle.getWidth()*.75;
        double fourth = paddleLPos + paddle.getWidth();

        if (ballLPos < first)
            ball.setxVelocity(-1);
        if (ballLPos >= first && ballLPos < second)
            ball.setxVelocity(-.5);
        if (ballLPos >= second && ballLPos < third)
            ball.setxVelocity(0);
        if (ballLPos >= third && ballLPos < fourth)
            ball.setxVelocity(.5);
        if (ballLPos > fourth)
            ball.setxVelocity(1);
    }


    /**
     * Checks intersection with ball
     *
     * A nice way to think about the logic here is to just ask the question, when does it not intersect?
     * Either the object is above, below, right or left. Then negate that.
     *
     * @param other The object to check against the ball
     * @return True if intersect
     */

    public boolean intersects(IPositionable other) {

        boolean above = other.getMaxY() < ball.getY();
        boolean below = other.getY() > ball.getY() + ball.getHeight();
        boolean leftOf = other.getMaxX() < ball.getX();
        boolean rightOf = other.getX() > ball.getX() + ball.getWidth();

        return !(above || below || leftOf || rightOf);
    }

    /**
     * This method is built this way to handle the corner to corner collision mainly
     *
     * If collision occurs, in what direction should the object bounce?
     *
     * @param rect Object to check against
     * @return Direction of to move the ball
     */

    private Direction getDirection(IPositionable rect){

        // Center positions
        double bx = ball.getX() + ball.getWidth()/2;
        double by = ball.getY() + ball.getHeight()/2;
        double rx = rect.getX() + rect.getWidth()/2;
        double ry = rect.getY() + rect.getHeight()/2;

        // Ball relative to -
        boolean above = by < ry;
        boolean leftOf = bx < rx;

        if(Math.abs(rx-bx) < Math.abs(ry-by)){ // distance from bx to rx is less than ry to by, then we have a vertical bounce
            if(above)
                return Direction.UP;
            else
                return Direction.DOWN;
        }
        else{
            if(leftOf)
                return Direction.LEFT;
            else
                return Direction.RIGHT;
        }
    }

    /**
     * Sets velocity of paddle
     * @param velocity speed / dx
     */
    // --- Used by GUI  ------------------------

    public void setPaddleVelocity(double velocity){
        paddle.setVelocity(velocity);
        //paddle.move();
    }

    /**
     * Get list of placeable objects
     * @return List of placeable objects like ball, paddle, walls and bricks
     */

    public List<IPositionable> getPositionables() {
        List<IPositionable> list = new ArrayList();
        list.add(ball);
        list.add(paddle);
        list.addAll(walls);
        list.addAll(bricks);
        return list;  // TODO return all objects to be rendered by GUI
    }

    public int getPoints() {
        return points;
    }

    public int getnBalls() {
        return nBalls;
    }
}
