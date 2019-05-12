package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.GameBoard;
import de.tum.in.ase.eist.GameBoardUI;
import de.tum.in.ase.eist.Move;
import de.tum.in.ase.eist.PokemonData;
import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.car.Pokemon;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.util.Random;

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
        final Random rand = new Random();

        final int animationFrameCount = 50;
        int animationIndex = 0;


        boolean isPlayerTurn = true;

        Move move = null;
        PokemonData currentPokemon = player;

        while (gameBoard.isRunning() && player.getHealth() > 0 && pokemon.getHealth() > 0) {
            ui.clear(gc);

            gc.drawImage(player.icon, playerPos.getX(), playerPos.getY(), SIZE, SIZE);
            gc.drawImage(pokemon.icon, pokemonPos.getX(), pokemonPos.getY(), SIZE, SIZE);

            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText(player.getName() + ": " + player.getHealth() + " / " + player.getMaxHealth(),
                    ui.getWidth() - playerPos.getX() - 80, playerPos.getY() + SIZE / 2.0);
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.fillText(pokemon.getName() + ": " + pokemon.getHealth() + " / " + pokemon.getMaxHealth(),
                    ui.getWidth() - pokemonPos.getX(), pokemonPos.getY() + SIZE / 2.0);
            gc.setFill(GameBoardUI.BACKGROUND_COLOR);

            if (move != null) {
                animationIndex++;

                if (animationIndex >= animationFrameCount) {
                    PokemonData otherPokemon = currentPokemon == player ? pokemon : player;
                    otherPokemon.damage(move.strength);
                    System.out.println(currentPokemon.getName() + " used " + move.name + " with a strength of " + move.strength);

                    animationIndex = 0;
                    move = null;

                    isPlayerTurn = !isPlayerTurn;
                    currentPokemon = isPlayerTurn ? player : pokemon;
                }

                ui.getToolBar().setHealth(player.getHealth());
            } else {
                move = currentPokemon.getMoves()[rand.nextInt(currentPokemon.getMoves().length)];
            }
            sleep(ui.SLEEP_TIME);
        }

        Car playerCar = gameBoard.getPlayerCar();
        Car pokemonCar = car1 == playerCar ? car2 : car1;

        return player.getHealth() > 0 ? playerCar : pokemonCar;
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
