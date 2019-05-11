package de.tum.in.ase.eist;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

public class PokemonData {

    public static class MetaInfo {
        public final int health;
//        public int type;

        public MetaInfo(int health) {
            this.health = health;
        }
    }

    public static final MetaInfo[] DEFAULT_INFO = new MetaInfo[]{
            new MetaInfo(200), //Pikachu
            new MetaInfo(120), //Glurak
            new MetaInfo(70), //Tauboss
            new MetaInfo(150), //Dragoran
            new MetaInfo(200), //Quirtel
            new MetaInfo(300), //Walraisa
    };

    public int health;
    public final int index;

    public final Image icon;

    public PokemonData(int index) {
        this.index = index;
        this.icon = getIcon("pokemon" + index + ".gif");
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
        final MetaInfo info = DEFAULT_INFO[index];
        this.health = info.health;
    }
}
