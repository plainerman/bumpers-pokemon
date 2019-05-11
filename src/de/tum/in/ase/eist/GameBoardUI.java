package de.tum.in.ase.eist;

import de.tum.in.ase.eist.car.Car;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

/**
 * This class implements the user interface for steering the player car. The
 * user interface is implemented as a Thread that is started by clicking the
 * start button on the tool bar and stops by the stop button.
 */
public class GameBoardUI extends Canvas implements Runnable {
    private static Color backgroundColor = Color.WHITE;
    public static int SLEEP_TIME = 1000 / 25; // this gives us 25fps
    public static Dimension2D DEFAULT_SIZE = new Dimension2D(500, 300);
    // attribute inherited by the JavaFX Canvas class
    GraphicsContext graphicsContext = this.getGraphicsContext2D();

    // thread responsible for starting game
    private Thread theThread;

    // user interface objects
    private GameBoard gameBoard;
    private Dimension2D size;
    private Toolbar toolBar;
    public static boolean debug = false;

    /**
     * Sets up all attributes, starts the mouse steering and sets up all graphics
     *
     * @param toolBar used to start and stop the game
     */
    public GameBoardUI(Toolbar toolBar) {
        this.toolBar = toolBar;
        this.size = getPreferredSize();
        this.gameBoard = new GameBoard(this, this.size);
        this.widthProperty().set(this.size.getWidth());
        this.heightProperty().set(this.size.getHeight());
        this.size = new Dimension2D(getWidth(), getHeight());
        this.steerCar();
        gameSetup();
        if (GraphicsEnvironment.isHeadless()) debug = false;
    }

    /**
     * Called after starting the game thread
     * Constantly updates the game board and renders graphics
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (this.gameBoard.isRunning()) {
            // updates car positions and re-renders graphics
            this.gameBoard.update();
            paint(this.graphicsContext);
            try {
                Thread.sleep(SLEEP_TIME); // milliseconds to sleep
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @return current gameBoard
     */
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    /**
     * @return preferred gameBoard size
     */
    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Removes all existing cars from the game board and re-adds them. Status bar is
     * set to default value. Player car is reset to default starting position.
     * Renders graphics.
     */
    public void gameSetup() {
        this.gameBoard.resetCars();
        paint(this.graphicsContext);
        this.toolBar.resetToolBarButtonStatus(false);
    }

    /**
     * Steers the car by handling MOUSE_PRESSED Events
     */
    public void steerCar() {
        MouseSteering mouseSteering = new MouseSteering(this, this.gameBoard.getPlayerCar());
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseSteering.mouseHandler);
    }

    /**
     * Starts the GameBoardUI Thread, if it wasn't running. Starts the game board,
     * which causes the cars to change their positions (i.e. move). Renders graphics
     * and updates tool bar status.
     */
    public void startGame() {
        if (!this.gameBoard.isRunning()) {
            this.gameBoard.startGame();
            this.theThread = new Thread(this);
            this.theThread.start();
            paint(this.graphicsContext);
            this.toolBar.resetToolBarButtonStatus(true);
        }
    }

    /**
     * Render the graphics of the whole game by iterating through the cars of the
     * game board at render each of them individually.
     *
     * @param graphics used to draw changes
     */
    private void paint(GraphicsContext graphics) {
        clear(graphics);

        for (Car car : this.gameBoard.getCars()) {
            paintCar(car, graphics);
        }
        // render player car
        paintCar(this.gameBoard.getPlayerCar(), graphics);
    }

    public void clear(GraphicsContext graphics) {
        clear(graphics, backgroundColor);
    }

    public void clear(GraphicsContext graphics, Color color) {
        graphics.setFill(color);
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Show image of a car at the current position of the car.
     *
     * @param car      to be drawn
     * @param graphics used to draw changes
     */
    public void paintCar(Car car, GraphicsContext graphics) {
        if (!car.isCrunched) {
            Point2D carPosition = car.getPosition();
            Point2D canvasPosition = convertPosition(carPosition);
            graphics.drawImage(car.getIcon(), canvasPosition.getX(), canvasPosition.getY(),
                    car.getSize().getWidth(), car.getSize().getHeight());

            if (debug) {
                drawRectangle(graphics, car.getRectangle());
            }
        }
    }

    private void drawRectangle(GraphicsContext gc, Rectangle rect) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        gc.strokeRect(rect.getX(),
                rect.getY(),
                rect.getWidth(),
                rect.getHeight());
    }

    /**
     * Converts position of car to position on the canvas
     *
     * @param toConvert the point to be converted
     */
    public Point2D convertPosition(Point2D toConvert) {
        return new Point2D(toConvert.getX(), getHeight() - toConvert.getY());
    }

    /**
     * Stops the game board and set the tool bar to default values.
     */
    public void stopGame() {
        if (this.gameBoard.isRunning()) {
            this.gameBoard.stopGame();
            this.toolBar.resetToolBarButtonStatus(false);
        }
    }
}
