package de.tum.in.ase.eist;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

public class PokemonData {

    public static class MetaInfo {
        public final String name;
        public final int health;
        public Move[] moves;

        public MetaInfo(String name, int health, Move... moves) {
            this.name = name;
            this.health = health;
            this.moves = moves;
        }
    }

    public static final MetaInfo[] DEFAULT_INFO = new MetaInfo[]{
            new MetaInfo("Pikachu", 500, Move.QUICK_ATTACK, Move.TAIL_WHIP, Move.THUNDER_BOLT, Move.THUNDER_SHOCK),
            new MetaInfo("Charizard", 120, Move.TAIL_WHIP, Move.FLARE_BLITZ, Move.HEAT_WAVE),
            new MetaInfo("Pidgeotto", 70, Move.TAIL_WHIP, Move.QUICK_ATTACK, Move.RAZOR_WIND, Move.WING_ATTACK),
            new MetaInfo("Dragonite", 150, Move.TAIL_WHIP, Move.DRAGON_TAIL, Move.SLAM),
            new MetaInfo("Torkoal", 200, Move.HEAT_WAVE, Move.FLARE_BLITZ, Move.EARTH_QUAKE),
            new MetaInfo("Walrein", 300, Move.SLAM, Move.ICE_BALL, Move.BLIZZARD),
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
