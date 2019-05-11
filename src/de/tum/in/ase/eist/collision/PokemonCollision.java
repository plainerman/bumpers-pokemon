package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.GameBoard;
import de.tum.in.ase.eist.car.Car;
import javafx.scene.shape.Rectangle;

public class PokemonCollision extends Collision {
    private GameBoard gameBoard;
    public PokemonCollision(GameBoard gameBoard, Car car1, Car car2) {
        super();
        this.car1 = car1;
        this.car2 = car2;
        this.gameBoard = gameBoard;
        this.isCollision = detectCollision();
    }

    @Override
    public boolean detectCollision() {
        if (gameBoard.getPlayer() == null) return false;
        if (car1 != gameBoard.getPlayerCar() && car2 != gameBoard.getPlayerCar()) return false;

        Rectangle r1 = car1.getRectangle();
        Rectangle r2 = car2.getRectangle();
        return r1.getBoundsInParent().intersects(r2.getBoundsInParent());
    }

    @Override
    public Car evaluate() {
        return super.evaluate();
    }
}
