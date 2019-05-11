package de.tum.in.ase.eist;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

public class PokemonData {

    public static class MetaInfo {
        public final String name;
        public final int health;
//        public int type;

        public MetaInfo(String name, int health) {
            this.name = name;
            this.health = health;
        }
    }

    public static final MetaInfo[] DEFAULT_INFO = new MetaInfo[]{
            new MetaInfo("Pikachu", 200),
            new MetaInfo("Charizard", 120),
            new MetaInfo("Pidgeotto", 70),
            new MetaInfo("Dragonite", 150),
            new MetaInfo("Torkoal", 200),
            new MetaInfo("Walrein", 300),
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
