package de.tum.in.ase.eist;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

public class PokemonData {

//    public class MetaInfo {
//        public int health;
//        public int type;
//    }
//
//    public static final MetaInfo[] DEFAULT_INFO = new MetaInfo[]{
//
//    };
//
//    public final MetaInfo info;
    public int health;
    public final int index;

    public final Image icon;
    public final Image animatedIcon;

    public PokemonData(int index) {
        this.index = index;
        this.icon = getIcon("pokemon" + index + ".png");
        this.animatedIcon = getIcon("pokemon" + index + ".gif");

        reset();
    }

    public Image getIcon(String path) {
        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResource(path).openStream();
        } catch (IOException e) {
            return null;
        }
        return new Image(inputStream);
    }

    public void reset() {
        health = 100; //TODO set correct health
    }
}
