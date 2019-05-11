package de.tum.in.ase.eist.car;

public class CustomCar extends Car {
    private static final String DEFAULT_CUSTOM_CAR_IMAGE_FILE = "CustomCar.gif";

    /**
     * Constructor, taking the maximum coordinates of the game board. Each car
     * gets a random X and Y coordinate, a random direction and a random speed
     * <p>
     * The position of the car cannot be larger then the parameters, i.e. the
     * dimensions of the game board
     *
     * @param maxX Maximum x coordinate (width) of the game board
     * @param maxY Maximum y coordinate (height) of the game board
     */
    public CustomCar(int maxX, int maxY, double height) {
        super(maxX, maxY, height);
        this.MIN_SPEED = 5;
        this.MAX_SPEED = 10;
        this.setRandomSpeed();
        this.setImage(DEFAULT_CUSTOM_CAR_IMAGE_FILE);
    }

    public CustomCar(int maxX, int maxY) {
        super(maxX, maxY);
    }
}
