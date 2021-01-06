package breakout.model;


import breakout.event.EventBus;
import breakout.event.ModelEvent;
import breakout.view.BreakoutGUI;

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


    public Breakout(Ball ball, Paddle paddle, List<Brick> bricks){
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
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
        if (ball.getY() > GAME_HEIGHT)
            if (getnBalls() != 0) {
                ball = new Ball();
                nBalls--;
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.LOSE_LIFE, ""));
            } else {
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GAME_OVER, ""));
                //BreakoutGUI.showGameOverScreen(this);
            }
    }

    // ----- Helper methods--------------
    private void doWallCollision(){
        if(ball.getX() < 0 || ball.getMaxX() > GAME_WIDTH)
            ball.flipxVelocity();
        if(ball.getY() < 0) {
            ball.flipyVelocity();
        }
    }

    // Used for functional decomposition
    public static int randomInteger(int min, int max) {
        Random gen = new Random(System.currentTimeMillis());
        return gen.nextInt((max - min) + 1) + min;
    }

    private void doBallBrickCollision(){
        for (Brick b : bricks) {
            if(intersects(b) && getDirection(b) == Direction.UP){
                points += b.getPoints();
                toRemove.add(b);
                ball.setyVelocity(-Math.abs(ball.getyVelocity()));
            }
            else if(intersects(b) && getDirection(b) == Direction.DOWN){
                points += b.getPoints();
                toRemove.add(b);
                ball.setyVelocity(Math.abs(ball.getyVelocity()));
            }
            else if(intersects(b) && getDirection(b) == Direction.LEFT){
                points += b.getPoints();
                toRemove.add(b);
                ball.setxVelocity(-Math.abs(ball.getxVelocity()));
            }
            else if(intersects(b) && getDirection(b) == Direction.RIGHT){
                points += b.getPoints();
                toRemove.add(b);
                ball.setxVelocity(Math.abs(ball.getxVelocity()));
            }
            if (intersects(b))
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK, ""));
        }
        bricks.removeAll(toRemove);
        toRemove.clear();
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

    private void doBallPaddleCollision(){
        if(intersects(paddle) && ball.getMaxY() < paddle.getY() + paddle.getHeight()/2) {
            ball.setyVelocity(-Math.abs(ball.getyVelocity()));
            setBounceDirection(ball, paddle);
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE, ""));
        }

//        if(intersects(paddle) && getDirection(paddle) == Direction.UP){
//            ball.setyVelocity(-Math.abs(ball.getyVelocity()));
//        }
//        else if(intersects(paddle) && getDirection(paddle) == Direction.DOWN){
//            ball.setyVelocity(Math.abs(ball.getyVelocity()));
//        }
//        else if(intersects(paddle) && getDirection(paddle) == Direction.LEFT){
//            ball.setxVelocity(-Math.abs(ball.getxVelocity()));
//        }
//        else if(intersects(paddle) && getDirection(paddle) == Direction.RIGHT){
//            ball.setxVelocity(Math.abs(ball.getxVelocity()));
//        }
    }

    public boolean intersects(IPositionable other) {

        boolean above = other.getMaxY() < ball.getY();
        boolean below = other.getY() > ball.getY() + ball.getHeight();
        boolean leftOf = other.getMaxX() < ball.getX();
        boolean rightOf = other.getX() > ball.getX() + ball.getWidth();

        return !(above || below || leftOf || rightOf);
    }

    private Direction getDirection(IPositionable rect){

        // Center positions
        double bx = ball.getX() + ball.getWidth()/2;
        double by = ball.getY() + ball.getHeight()/2;
        double rx = rect.getX() + rect.getWidth()/2;
        double ry = rect.getY() + rect.getHeight()/2;

        // Ball relative to -
        boolean above = by < ry;
        boolean below = by > ry;
        boolean leftOf = bx < rx;
        boolean rightOf = bx > rx;

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


    // Used for functional decomposition



    // --- Used by GUI  ------------------------

    public void setPaddleVelocity(double velocity){
        paddle.setVelocity(velocity);
        //paddle.move();
    }

    public List<IPositionable> getPositionables() {
        List<IPositionable> list = new ArrayList();
        list.add(ball);
        list.add(paddle);
        //list.addAll(walls);
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
