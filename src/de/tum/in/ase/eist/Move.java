package de.tum.in.ase.eist;

import static de.tum.in.ase.eist.Type.*;

public class Move {
    public static final Move QUICK_ATTACK = new Move(NORMAL, "Quick Attack", 40);
    public static final Move TAIL_WHIP = new Move(NORMAL, "Thail Whip", 70);
    public static final Move SLAM = new Move(NORMAL, "Slam", 70);

    public static final Move DRAGON_TAIL = new Move(DRAGON, "Dragon Tail", 60);

    public static final Move WING_ATTACK = new Move(FLYING, "Wing Attack", 60);
    public static final Move RAZOR_WIND = new Move(FLYING, "Razor Wind", 80);

    public static final Move HEAT_WAVE = new Move(FIRE, "Heat Wave", 80);
    public static final Move FLARE_BLITZ = new Move(FIRE, "Flare Blitz", 100);

    public static final Move THUNDER_SHOCK = new Move(ELECTRO, "Thunder Shock", 70);
    public static final Move THUNDER_BOLT = new Move(ELECTRO, "Thunder Bolt", 90);

    public static final Move EARTH_QUAKE = new Move(GROUND, "Earthquake", 100);

    public static final Move BLIZZARD = new Move(ICE, "Blizzard", 110);
    public static final Move ICE_BALL = new Move(ICE, "Ice Ball", 40);

    public static final Move MAGICAL_LEAF = new Move(GRASS, "Magical Leaf", 60);
    public static final Move SOLAR_BEAM = new Move(GRASS, "Solar Beam", 100);

    public final Type type;
    public final String name;
    public final int strength;

    public Move(Type type, String name, int strength) {
        this.type = type;
        this.name = name;
        this.strength = strength;
    }
}
