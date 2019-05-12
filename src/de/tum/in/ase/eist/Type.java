package de.tum.in.ase.eist;

public enum Type {
    FLYING, ELECTRIC, ICE, DRAGON, FIRE, NORMAL, GROUND, GRASS;
    public static final double[][] FACTOR = new double[][]{
            {1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0}, // Flying attack
            {2.0, 1.0, 1.0, 0.5, 1.0, 1.0, 0.0, 0.5}, // Electric attack
            {2.0, 1.0, 1.0, 2.0, 0.5, 1.0, 2.0, 2.0}, // Ice attack
            {1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0}, // Dragon attack
            {1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 2.0}, // Fire attack
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, // Normal attack
            {0.0, 2.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5}, // Ground attack
            {0.5, 1.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5}, // Grass attack
    };
}
