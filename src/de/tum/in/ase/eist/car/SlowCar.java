package de.tum.in.ase.eist.car;

public class SlowCar extends Car {
	
	public static String DEFAULT_SLOW_CAR_IMAGE_FILE = "SlowCar.gif";

	/**
	 * Constructor for a SlowCar
	 * 
	 * @param maxX Maximum x coordinate (width) of the game board
	 * @param maxY Maximum y coordinate (height) of the game board
	 */
	public SlowCar(int maxX, int maxY, double height) {
		super(maxX, maxY, height);
		this.MIN_SPEED = 2;
		this.MAX_SPEED = 5;
		this.setRandomSpeed();
		this.setImage(DEFAULT_SLOW_CAR_IMAGE_FILE); 
	}
}
