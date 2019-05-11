package de.tum.in.ase.eist;

import java.util.Optional;

import de.tum.in.ase.eist.car.Pokemon;
import de.tum.in.ase.eist.collision.PokemonCollision;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * This class visualizes the tool bar with start, stop and exit buttons above
 * the game board.
 */
public class Toolbar extends ToolBar {
    private BumpersApplication gameWindow;
    private Button start;
    private Button stop;
    private Label pokemonLabel;
    private Label healthLabel;

    public Toolbar(BumpersApplication gameWindow) {
        this.start = new Button("Start");
        this.stop = new Button("Stop");
        this.pokemonLabel = new Label(PokemonData.DEFAULT_INFO[0].name + ": ");
        this.healthLabel = new Label("" + PokemonData.DEFAULT_INFO[0].health);
        initActions();
        this.getItems().addAll(start, new Separator(), stop, new Separator(), pokemonLabel, healthLabel);
        this.setGameWindow(gameWindow);
    }

    public void setHealth(int health) {
        Platform.runLater(() -> {
            this.healthLabel.setText("" + health);
        });
    }

    /**
     * Initialises the actions
     */
    private void initActions() {
        this.start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getGameWindow().gameBoardUI.startGame();
            }
        });

        this.stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Toolbar.this.getGameWindow().gameBoardUI.stopGame();

                ButtonType YES = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to stop the game ?", YES, NO);
                alert.setTitle("Stop Game Confirmation");
                alert.setHeaderText("");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == YES) {
                    getGameWindow().gameBoardUI.gameSetup();
                } else {
                    getGameWindow().gameBoardUI.startGame();
                }
            }
        });
    }

    /**
     * Resets the toolbar button status
     *
     * @param running Used to disable/enable buttons
     */
    public void resetToolBarButtonStatus(boolean running) {
        this.start.setDisable(running);
        this.stop.setDisable(!running);
    }

    /**
     * @return current gameWindow
     */
    public BumpersApplication getGameWindow() {
        return this.gameWindow;
    }

    /**
     * @param gameWindow New gameWindow to be set
     */
    public void setGameWindow(BumpersApplication gameWindow) {
        this.gameWindow = gameWindow;
    }
}
