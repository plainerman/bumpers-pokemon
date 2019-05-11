package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.GameBoard;
import de.tum.in.ase.eist.GameBoardUI;
import de.tum.in.ase.eist.PokemonData;
import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.car.Pokemon;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PokemonCollision extends Collision {
    private GameBoard gameBoard;
    private Car winner;
    public static final int SIZE = 100;

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
        if (!gameBoard.isMoveCars()) return false;
        if (car1 != gameBoard.getPlayerCar() && car2 != gameBoard.getPlayerCar()) return false;

        Rectangle r1 = car1.getRectangle();
        Rectangle r2 = car2.getRectangle();
        return r1.getBoundsInParent().intersects(r2.getBoundsInParent());
    }

    @Override
    public Car evaluateLoser() {
        if (winner == null) return null;
        return winner == car2 ? car1 : car2;
    }

    @Override
    public Car evaluate() {
        gameBoard.setMoveCars(false);

        GameBoardUI ui = gameBoard.getUi();
        GraphicsContext gc = ui.getGraphicsContext2D();

        final long duration = 250;
        int i = 0;
        while (this.gameBoard.isRunning() && i < 4) {
            ui.clear(gc, Color.BLACK);
            paintCarsAndSleep(ui, gc, duration);

            ui.clear(gc, Color.WHITE);
            paintCarsAndSleep(ui, gc, duration);

            i++;
        }

        ui.clear(gc, Color.BLACK);
        paintCarsAndSleep(ui, gc, duration);

        PokemonData player = gameBoard.getPlayer().getCar().pokemon;
        PokemonData pokemon = car1 == gameBoard.getPlayerCar() ? ((Pokemon) car2).data : ((Pokemon) car1).data;

        Point2D playerPos = new Point2D(-SIZE, ui.getHeight() - SIZE - 50);
        Point2D pokemonPos = new Point2D(ui.getWidth(), 5);
        for (i = 0; i <= SIZE && gameBoard.isRunning(); i++) {
            ui.clear(gc);
            gc.drawImage(player.icon, playerPos.getX(), playerPos.getY(), SIZE, SIZE);
            gc.drawImage(pokemon.icon, pokemonPos.getX(), pokemonPos.getY(), SIZE, SIZE);

            playerPos = playerPos.add(1.5, 0);
            pokemonPos = pokemonPos.add(-1.5, 0);

            sleep(20);
        }

        winner = evaluate(gameBoard, ui, gc, player, pokemon, playerPos, pokemonPos);

        gameBoard.setMoveCars(true);
        gameBoard.getAudioPlayer().endCrashSound(this.gameBoard.isRunning());

        return winner;
    }

    protected Car evaluate(GameBoard gameBoard, GameBoardUI ui, GraphicsContext gc, PokemonData player,
                           PokemonData pokemon, Point2D playerPos, Point2D pokemonPos) {
        int i = 0;
        while (gameBoard.isRunning() && i++ < 100) {
            ui.clear(gc);

            gc.drawImage(player.icon, playerPos.getX(), playerPos.getY(), SIZE, SIZE);
            gc.drawImage(pokemon.icon, pokemonPos.getX(), pokemonPos.getY(), SIZE, SIZE);


            player.health -= 1;
            ui.getToolBar().setHealth(player.health);
            sleep(ui.SLEEP_TIME);
        }

        return gameBoard.getPlayerCar();
    }

    private void paintCarsAndSleep(GameBoardUI ui, GraphicsContext gc, long duration) {
        ui.paintCar(car1, gc);
        ui.paintCar(car2, gc);
        sleep(duration);
    }

    private void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
        }
    }
}
