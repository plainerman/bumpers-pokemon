package de.tum.in.ase.eist.car;

public class FastCar extends Car {

	public static String DEFAULT_FAST_CAR_IMAGE_FILE = "FastCar.gif";

	/**
	 * Constructor for a FastCar.
	 * 
	 * @param maxX Maximum x coordinate (width) of the game board
	 * @param maxY Maximum y coordinate (height) of the game board
	 */
	public FastCar(int maxX, int maxY, double height) {
		super(maxX, maxY, height);
		this.MIN_SPEED = 5;
		this.MAX_SPEED = 10;
		this.setRandomSpeed();
		this.setImage(DEFAULT_FAST_CAR_IMAGE_FILE); 
	}
}
