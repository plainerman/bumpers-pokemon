package de.tum.in.ase.eist.car;


import de.tum.in.ase.eist.PokemonData;
import javafx.geometry.Dimension2D;

public class FastCar extends Car {

    public static String DEFAULT_FAST_CAR_IMAGE_FILE = "FastCar.gif";

    public final PokemonData pokemon;

    /**
     * Constructor for a FastCar.
     *
     * @param maxX Maximum x coordinate (width) of the game board
     * @param maxY Maximum y coordinate (height) of the game board
     */
    public FastCar(int maxX, int maxY, double height) {
        super(maxX, maxY, height);
        this.MIN_SPEED = 5;
        this.MAX_SPEED = 10;
        this.setRandomSpeed();
        this.setImage(DEFAULT_FAST_CAR_IMAGE_FILE);

        pokemon = null;
    }

    public FastCar(Dimension2D size, double height) {
        super((int) size.getWidth(), (int) size.getHeight(), size, height);
        this.MIN_SPEED = 2.4;
        this.MAX_SPEED = 3.4;
        this.setRandomSpeed();
        this.setImage(DEFAULT_FAST_CAR_IMAGE_FILE);

        this.pokemon = new PokemonData(0);
    }

    public FastCar(int maxX, int maxY) {
        super(maxX, maxY);

        pokemon = null;
    }
}
