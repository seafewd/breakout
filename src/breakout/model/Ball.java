package breakout.model;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;

/*
 *    A Ball for the Breakout game
 */

public class Ball implements IPositionable{

    private double xPosition;
    private double yPosition;
    private double xVelocity;
    private double yVelocity;
    private double radius;

    private final double defaultxVelocity = 0.4;
    private final double defaultyVelocity = -1.9;
    private final double defaultxPosition = GAME_WIDTH/2;
    private final double defaultyPosition = GAME_HEIGHT/2;
    private double defaultRadius = 7;

    public Ball(double xPosition, double yPosition, double xVelocity, double yVelocity, double radius){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.radius = radius;
    }

    // Default velocity and radius
    public Ball(double xPosition, double yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        xVelocity = defaultxVelocity;
        yVelocity = defaultyVelocity;
        radius = defaultRadius;
    }

    // Default values
    public Ball(){
        xPosition = defaultxPosition;
        yPosition = defaultyPosition;
        xVelocity = defaultxVelocity;
        yVelocity = defaultyVelocity;
        radius = defaultRadius;
    }

    public void move(){
        xPosition += xVelocity;
        yPosition += yVelocity;
    }

    /**
     * Flip sign of velocity in the x direction
     */
    public void flipxVelocity(){
        xVelocity *= -1;
    }

    /**
     * Flip sign of velocity in the y direction
     */
    public void flipyVelocity(){
        yVelocity *= -1;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
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
        return xPosition + getWidth();
    }

    @Override
    public double getMaxY() {
        return yPosition + getHeight();
    }

    // Unused
    @Override
    public double getWidth() {
        return radius*2;
    }

    // Unused
    @Override
    public double getHeight() {
        return radius*2;
    }

    public double getRadius() {
        return radius;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

}
