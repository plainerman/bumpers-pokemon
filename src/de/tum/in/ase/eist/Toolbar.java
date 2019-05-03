package de.tum.in.ase.eist;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * This class visualizes the tool bar with start, stop and exit buttons above
 * the game board.
 *
 */
public class Toolbar extends ToolBar {
	private BumpersApplication gameWindow;
	private Button start;
	private Button stop;

	public Toolbar(BumpersApplication gameWindow) {
		this.start = new Button("Start");
		this.stop = new Button("Stop");
		initActions();
		this.getItems().addAll(start, new Separator(), stop);
		this.setGameWindow(gameWindow);
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
