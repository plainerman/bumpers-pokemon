package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.car.Car;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 * This class defines the behavior when two cars collide.
 */
public class Collision {

    protected Car car1;
    protected Car car2;
    public boolean isCollision;

    protected Collision() {

    }

    public Collision(Car car1, Car car2) {
        this.car1 = car1;
        this.car2 = car2;
        this.isCollision = detectCollision();
    }

    /**
     * Checks whether two cars have collided. The translation transformation shifts
     * a node from one place to another along one of the axes relative to its
     * initial position.
     *
     * @return boolean - true if collision is detected.
     */
    public boolean detectCollision() {

        Point2D p1 = car1.getPosition();
        Dimension2D d1 = car1.getSize();
        Rectangle r1 = new Rectangle(p1.getX(), p1.getY(), d1.getWidth(), d1.getHeight());
        Scale s1 = new Scale(3 * d1.getWidth() / 4, 3 * d1.getHeight() / 4);
        Translate t1 = new Translate(p1.getX() / 8, p1.getY() / 8);
        r1.getTransforms().addAll(s1, t1);

        Dimension2D d2 = car2.getSize();
        Point2D p2 = this.car2.getPosition();
        Rectangle r2 = new Rectangle(p2.getX(), p2.getY(), d2.getWidth(), d2.getHeight());
        Scale s2 = new Scale(3 * d2.getWidth() / 4, 3 * d2.getHeight() / 4);
        Translate t2 = new Translate(p2.getX() / 8, p2.getY() / 8);
        r2.getTransforms().addAll(s2, t2);

        return r1.getBoundsInParent().intersects(r2.getBoundsInParent());
    }

    /**
     * Evaluates winner of the collision
     *
     * @return winner Car
     */
    public Car evaluate() {

        // TODO 1: Collisions follow the "right before left" rule, and thus right-most cars win the collisions

        Point2D positionCar1 = this.car1.getPosition();
        Point2D positionCar2 = this.car2.getPosition();

        return positionCar1.getX() > positionCar2.getX() ? car1 : car2;
    }

    /**
     * Evaluates loser of the collision
     *
     * @return loser Car
     */
    public Car evaluateLoser() {
        Car winner = this.evaluate();
        if (this.car1.equals(winner)) {
            return this.car2;
        }
        return this.car1;
    }
}
