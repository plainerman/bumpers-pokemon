package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.car.Car;

import java.util.Random;

public class RandomCollision extends Collision {
    private final Random rand;

    public RandomCollision(Car car1, Car car2) {
        super(car1, car2);
        rand = new Random();
    }

    @Override
    public Car evaluate() {
        return rand.nextBoolean() ? car1 : car2;
    }
}
