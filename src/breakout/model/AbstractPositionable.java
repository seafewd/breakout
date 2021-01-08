package breakout.model;

public class AbstractPositionable implements IPositionable {
    protected double xPosition;
    protected double yPosition;
    private double width;
    private double height;

    public AbstractPositionable(double xPosition, double yPosition, double width, double height) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
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
