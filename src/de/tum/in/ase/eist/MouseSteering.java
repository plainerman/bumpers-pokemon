package de.tum.in.ase.eist;

import de.tum.in.ase.eist.car.Car;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * This class is responsible for the handling the MOUSE_PRESSED Event, 
 * i.e. the steering of the UserCar 
 */

public class MouseSteering  {

	private Car userCar;
	private GameBoardUI gameBoard;
	
	/**
	 * Constructor, creates a MouseSteering instance with a specific playingFlied and userCar
	 * @param playingField
	 * @param userCar
	 */
	public MouseSteering(GameBoardUI playingField, Car userCar) {
		this.userCar = userCar;
		this.gameBoard = playingField;
		this.getGameBoard().addEventHandler(MouseEvent.MOUSE_PRESSED, this.mouseHandler);
	}
	
	public Car getUserCar() {
		return this.userCar;
	}
	
	public void setUserCar(Car userCar) {
		this.userCar = userCar;
	}
	
	public GameBoardUI getGameBoard() {
		return this.gameBoard;
	}
	
	public void setGameBoard(GameBoardUI gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent e) {
        	Point2D carPosition = MouseSteering.this.getUserCar().getPosition();
        	Point2D clickPosition = MouseSteering.this.getGameBoard().convertPosition(new Point2D(e.getX(), e.getY()));
    		int deltaX = (int) (clickPosition.getX() - carPosition.getX());
    		deltaX = Math.abs(deltaX);
    		int deltaY = (int) (clickPosition.getY() - carPosition.getY());
    		double diff = ((double)deltaY) / ((double)deltaX);
    		double theta = Math.atan(diff);
    		int degree = (int)Math.toDegrees(theta);

    		if(clickPosition.getX() > carPosition.getX()) {
    			degree = 90 - degree;
    		} else {
    			degree = 270 + degree;
    		}
    		userCar.setDirection(degree);
        }
     
    };

}
