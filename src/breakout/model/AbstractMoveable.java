package breakout.model;

public class AbstractMoveable extends AbstractPositionable {

    private double xVelocity;
    private double yVelocity;

    public AbstractMoveable(double xPosition, double yPosition, double width, double height, double xVelocity, double yVelocity) {
        super(xPosition, yPosition, width, height);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

}
