package de.tum.in.ase.eist;

import java.util.ArrayList;
import java.util.List;

import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.car.FastCar;
import de.tum.in.ase.eist.car.SlowCar;
import de.tum.in.ase.eist.collision.Collision;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Creates all car objects, detects collisions, updates car positions, notifies
 * player about victory or defeat
 *
 */
public class GameBoard {

	// list of all active cars, does not contain player car
	private List<Car> cars = new ArrayList<>();
	// the player object with player car object
	private Player player;
	private AudioPlayer audioPlayer;
	private Dimension2D size;
	private MouseSteering mouseSteering;
	// list of all loser cars (needed for testing, DO NOT DELETE THIS)
	private List<Car> loserCars = new ArrayList<>();
	// true if game is running, false if game is stopped
	private boolean isRunning;
	// used for testing, DO NOT DELETE THIS
	public String result;
	public boolean printed;

	//constants
	public static int NUMBER_OF_SLOW_CARS = 5;
	public static int NUMBER_OF_TESLA_CARS = 2;
	
	/**
	 * Constructor, creates the gameboard based on size 
	 * @param size of the gameboard
	 */
	public GameBoard(Dimension2D size) {
		Car playerCar = new FastCar(250, 30);
		this.player = new Player(playerCar);
		this.audioPlayer = new AudioPlayer();
		this.size = size;
		this.result = "undefined";
		this.addCars();
	}

	/**
	 * Adds specified number of cars to the cars list, creates new object for each car
	 */
	public void addCars() {
		for (int i = 0; i < NUMBER_OF_SLOW_CARS; i++) {
			this.cars.add(new SlowCar((int)this.size.getWidth(), (int) this.size.getHeight()));
		}
	}

	/**
	 * Removes all existing cars from the car list, resets the position of the
	 * player car Invokes the creation of new car objects by calling addCars()
	 */
	public void resetCars() {
		this.player.getCar().reset((int) this.size.getHeight());
		this.cars.clear();
		addCars();
	}

	/**
	 * Checks if game is currently running by checking if the thread is running
	 * @return boolean isRunning
	 */
	public boolean isRunning() {
		return this.isRunning;
	}

	/**
	 * @return the gameboard's instance of MouseSteering
	 */
	public MouseSteering getMouseSteering() {
		return this.mouseSteering;
	}
	
	/**
	 * Used for testing only
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * @return list of cars
	 */
	public List<Car> getCars() {
		return this.cars;
	}

	/**
	 * @return the player's car
	 */
	public Car getPlayerCar() {
		return this.player.getCar();
	}
	
	/**
	 * @return the gameboard's instance of AudioPlayer
	 */
	public AudioPlayer getAudioPlayer() {
		return this.audioPlayer;
	}
	
	private void setPrinted(boolean printed) {
		this.printed = printed;
	}

	/**
	 * Updates the position of each car
	 */
	public void update() {
		moveCars();
	}

	/**
	 * Starts the game. Cars start to move and background music starts to play.
	 */
	public void startGame() {
		playMusic();
		this.isRunning = true;
	}

	/**
	 * Stops the game. Cars stop moving and background music stops playing.
	 */
	public void stopGame() {
		stopMusic();
		this.isRunning = false;
	}

	/**
	 * Starts the background music
	 */
	public void playMusic() {
		this.audioPlayer.playBackgroundMusic();
	}

	/**
	 * Stops the background music
	 */
	public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
	}

	/**
	 * @return list of loser cars
	 */
	public List<Car> getLoserCars() {
		return this.loserCars;
	}

	/**
	 * Iterate through list of cars (without the player car) and update each car's position 
	 * Update player car afterwards separately
	 */
	public void moveCars() {

		List<Car> cars = getCars();

		// maximum x and y values a car can have depending on the size of the game board
		int maxX = (int) size.getWidth();
		int maxY = (int) size.getHeight();

		// update the positions of the player car and the autonomous cars
		for (Car car : cars) {
			car.updatePosition(maxX, maxY);
		}

		player.getCar().updatePosition(maxX, maxY);

		// iterate through all cars (except player car) and check if it is crunched
		for (Car car : cars) {
			if (car.isCrunched()) {
				continue; // because there is no need to check for a collision
			}
			
			// TODO 4: Add a new collision type! 
			// Hint: Make sure to create a subclass of the class Collision 
			// and store it in the new Collision package.
			// Create a new collision object 
			// and check if the collision between player car and autonomous car evaluates as expected

			Collision collision = new Collision(player.getCar(), car);

			if (collision.isCollision) {
				Car winner = collision.evaluate();
				Car loser = collision.evaluateLoser();
				System.out.println(winner);
				loserCars.add(loser);
				audioPlayer.playCrashSound();
				
				// TODO 2: The loser car is crunched and stops driving
				
				// TODO 3: The player gets notified when he looses or wins the game
				// Hint: you should use the two methods 'showAsyncAlert(String)' and
				// 'isWinner()' below for your implementation
				
			}
		}
	}
	
	/**
	 * Method used to display alerts in moveCars() Java 8 Lambda Functions: java
	 * 8 lambda function without arguments Platform.runLater Function:
	 * https://docs.oracle.com/javase/8/javafx/api/javafx/application/Platform.html
	 * 
	 * @param message
	 *            you want to display as a String
	 */
	private void showAsyncAlert(String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(message);
			alert.showAndWait();
		});
		setPrinted(true);
	}

	/**
	 * If all cars are crunched, the player wins.
	 * 
	 * @return true if game is won
	 * @return false if game is not (yet) won
	 */
	private boolean isWinner() {
		for (Car car : getCars()) {
			if (car.isCrunched == false) {
				return false;
			}
		}
		return true;
	}
}
