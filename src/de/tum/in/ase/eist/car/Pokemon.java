package de.tum.in.ase.eist.car;

import de.tum.in.ase.eist.PokemonData;
import javafx.geometry.Dimension2D;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Pokemon extends Car {

    protected final int index;
    protected static final int POKEMON_COUNT = 5;

    public final PokemonData data;

    private static final Random rand = new Random();
    private static Set<Integer> usedIndices = new HashSet<>();

    public Pokemon(int maxX, int maxY, double height) {
        super(maxX, maxY, new Dimension2D(35, 35), height);
        this.MIN_SPEED = 1;
        this.MAX_SPEED = 2;
        this.setRandomSpeed();
        int tmpIndex = 0;
        for (int i = 0; i < 10; i++) {
            tmpIndex = rand.nextInt(POKEMON_COUNT) + 1;
            if (!usedIndices.contains(tmpIndex)) {
                usedIndices.add(tmpIndex);
                break;
            }
        }
        this.index = tmpIndex; // the first pokemon is the pokemon of the player
        this.setImage("pokemon" + this.index + ".png");

        data = new PokemonData(index);
    }

    public static void resetUsedIndices() {
        usedIndices.clear();
    }
}
