package de.tum.in.ase.eist.car;

import javafx.geometry.Dimension2D;

import java.util.Random;

public class Pokemon extends Car {

    protected final int index;
    protected static final int POKEMON_COUNT = 1;

    private static final Random rand = new Random();

    public Pokemon(int maxX, int maxY) {
        super(maxX, maxY, new Dimension2D(100, 100));
        this.MIN_SPEED = 2;
        this.MAX_SPEED = 7;
        this.setRandomSpeed();
        this.index = rand.nextInt(POKEMON_COUNT);
        this.setImage("pokemon" + index + ".png");
    }
}