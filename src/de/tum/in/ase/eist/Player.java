package de.tum.in.ase.eist;

import de.tum.in.ase.eist.car.Car;

/**
 * This class defines the player. Each player has its own car.
 *
 */
public class Player {

	private Car playerCar;

	/**
	 * Constructor that allocates a car to the player
	 * 
	 * @param car the car that should be the player's car
	 */
	public Player(Car car) {
		this.playerCar = car;
	}

	/**
	 * @param car the player's new car
	 */
	public void setCar(Car car) {
		this.playerCar = car;
	}

	/**
	 * @return The player's current car
	 */
	public Car getCar() {
		return this.playerCar;
	}

}
