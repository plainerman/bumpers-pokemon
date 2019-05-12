package de.tum.in.ase.eist;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

import static de.tum.in.ase.eist.Type.*;

public class PokemonData {
    public static class MetaInfo {
        public final String name;
        public final Type type;
        public final int health;
        public Move[] moves;

        public MetaInfo(String name, Type type, int health, Move... moves) {
            this.name = name;
            this.type = type;
            this.health = health;
            this.moves = moves;
        }
    }

    public static final MetaInfo[] DEFAULT_INFO = new MetaInfo[]{
            new MetaInfo("Pikachu", ELECTRIC, 1000, Move.QUICK_ATTACK, Move.TAIL_WHIP, Move.THUNDER_BOLT,
                    Move.THUNDER_SHOCK),
            new MetaInfo("Charizard", FIRE, 120, Move.TAIL_WHIP, Move.FLARE_BLITZ, Move.HEAT_WAVE),
            new MetaInfo("Pidgeotto", FLYING, 130, Move.TAIL_WHIP, Move.QUICK_ATTACK, Move.RAZOR_WIND, Move.WING_ATTACK),
            new MetaInfo("Dragonite", DRAGON, 123, Move.TAIL_WHIP, Move.DRAGON_TAIL, Move.SLAM),
            new MetaInfo("Meganium", GRASS, 180, Move.SLAM, Move.MAGICAL_LEAF, Move.SOLAR_BEAM),
            new MetaInfo("Torkoal", GROUND, 175, Move.HEAT_WAVE, Move.FLARE_BLITZ, Move.EARTH_QUAKE),
            new MetaInfo("Walrein", ICE, 210, Move.SLAM, Move.ICE_BALL, Move.BLIZZARD),
    };

    private int health;
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

    public MetaInfo getInfo() {
        return DEFAULT_INFO[index];
    }

    public Move[] getMoves() {
        return getInfo().moves;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return getInfo().health;
    }

    public String getName() {
        return getInfo().name;
    }

    public void damage(int value) {
        this.health = Math.max(0, this.health - value);
    }

    public Type getType() {
        return getInfo().type;
    }
}
