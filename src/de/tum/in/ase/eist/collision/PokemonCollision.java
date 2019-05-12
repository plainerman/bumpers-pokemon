package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.GameBoard;
import de.tum.in.ase.eist.GameBoardUI;
import de.tum.in.ase.eist.Move;
import de.tum.in.ase.eist.PokemonData;
import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.car.Pokemon;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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

    private Move move;
    private GridPane gridPane;

    private int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    protected Car evaluate(GameBoard gameBoard, GameBoardUI ui, GraphicsContext gc, final PokemonData player,
                           PokemonData pokemon, Point2D playerPos, Point2D pokemonPos) {
        final Random rand = new Random();

        final int animationFrameCount = 50;
        int animationIndex = 0;


        move = null;
        boolean isPlayerTurn = true;

        PokemonData currentPokemon = player;

        generateGridPane(ui, player);

        showMoves(ui);

        while (gameBoard.isRunning() && player.getHealth() > 0 && pokemon.getHealth() > 0) {
            ui.clear(gc);
            renderFight(ui, gc, player, pokemon, playerPos, pokemonPos, true);

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

                    if (isPlayerTurn) {
                        showMoves(ui);
                    }
                }

                ui.getToolBar().setHealth(player.getHealth());
            } else {
                if (!isPlayerTurn) {
                    move = currentPokemon.getMoves()[rand.nextInt(currentPokemon.getMoves().length)];
                }
            }
            sleep(GameBoardUI.SLEEP_TIME);
        }

        ui.getStackPane().getChildren().remove(gridPane);

        final int playerFactor = player.getHealth() > 0 ? 0 : 1;
        final int pokemonFactor = pokemon.getHealth() > 0 ? 0 : -1;

        for (int i = 0; i < SIZE + 60 && gameBoard.isRunning(); i++) {
            playerPos = playerPos.add(0, playerFactor);
            pokemonPos = pokemonPos.add(0, pokemonFactor);

            ui.clear(gc);
            renderFight(ui, gc, player, pokemon, playerPos, pokemonPos, false);

            sleep(GameBoardUI.SLEEP_TIME / 4);
        }

        sleep(200);

        Car playerCar = gameBoard.getPlayerCar();
        Car pokemonCar = car1 == playerCar ? car2 : car1;

        return player.getHealth() > 0 ? playerCar : pokemonCar;
    }

    private void showMoves(GameBoardUI ui) {
        Platform.runLater(() -> {
            ui.getStackPane().getChildren().add(gridPane);
        });
    }

    private void generateGridPane(GameBoardUI ui, PokemonData player) {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 30, 30, 0));
        gridPane.setAlignment(Pos.BOTTOM_RIGHT);

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        for (int i = 0; i < player.getMoves().length; i++) {
            final int index = i;
            final Button button = new Button(player.getMoves()[i].name);

            button.setOnAction(e -> {
                ui.getStackPane().getChildren().remove(gridPane);
                move = player.getMoves()[index];
            });

            gridPane.add(button, getBit(i, 0), getBit(i, 1));
        }
    }

    private void renderFight(GameBoardUI ui, GraphicsContext gc, PokemonData player, PokemonData pokemon,
                             Point2D playerPos, Point2D pokemonPos, boolean renderHealth) {
        gc.drawImage(player.icon, playerPos.getX(), playerPos.getY(), SIZE, SIZE);
        gc.drawImage(pokemon.icon, pokemonPos.getX(), pokemonPos.getY(), SIZE, SIZE);

        if (renderHealth) {
            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText(player.getName() + ": " + player.getHealth() + " / " + player.getMaxHealth(),
                    ui.getWidth() - playerPos.getX() - 80, playerPos.getY() + SIZE / 2.0);
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.fillText(pokemon.getName() + ": " + pokemon.getHealth() + " / " + pokemon.getMaxHealth(),
                    ui.getWidth() - pokemonPos.getX(), pokemonPos.getY() + SIZE / 2.0);
            gc.setFill(GameBoardUI.BACKGROUND_COLOR);
        }
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
