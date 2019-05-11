package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.car.Car;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class PokemonCollision extends Collision {
    public PokemonCollision(Car car1, Car car2) {
        super(car1, car2);
    }

    @Override
    public boolean detectCollision() {
        Point2D p1 = car1.getPosition();
        Dimension2D d1 = car1.getSize();
        Rectangle r1 = car1.getRectangle();
//        Scale s1 = new Scale(3 * d1.getWidth() / 4, 3 * d1.getHeight() / 4);
//        Translate t1 = new Translate(p1.getX() / 8, p1.getY() / 8);
//        r1.getTransforms().addAll(s1, t1);

        Dimension2D d2 = car2.getSize();
        Point2D p2 = this.car2.getPosition();
        Rectangle r2 = car2.getRectangle();
//        Scale s2 = new Scale(3 * d2.getWidth() / 4, 3 * d2.getHeight() / 4);
//        Translate t2 = new Translate(p2.getX() / 8, p2.getY() / 8);
//        r2.getTransforms().addAll(s2, t2);


        return r1.getBoundsInParent().intersects(r2.getBoundsInParent());
    }
}
