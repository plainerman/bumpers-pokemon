package de.tum.in.ase.eist;

public class Move {
    //TODO: accuracy
    //TODO: types
    public static final Move QUICK_ATTACK = new Move("Quick Attack", 40);
    public static final Move TAIL_WHIP = new Move("Thail Whip", 70);
    public static final Move SLAM = new Move("Slam", 70);

    public static final Move DRAGON_TAIL = new Move("Dragon Tail", 60);

    public static final Move WING_ATTACK = new Move("Wing Attack", 60);
    public static final Move RAZOR_WIND = new Move("Razor Wind", 80);

    public static final Move HEAT_WAVE = new Move("Heat Wave", 80);
    public static final Move FLARE_BLITZ = new Move("Flare Blitz", 100);

    public static final Move THUNDER_SHOCK = new Move("Thunder Shock", 70);
    public static final Move THUNDER_BOLT = new Move("Thunder Bolt", 90);

    public static final Move EARTH_QUAKE = new Move("Earthquake", 100);

    public static final Move BLIZZARD = new Move("Blizzard", 110);
    public static final Move ICE_BALL = new Move("Ice Ball", 40);

    public static final Move MAGICAL_LEAF = new Move("Magical Leaf", 60);
    public static final Move SOLAR_BEAM = new Move("Solar Beam", 100);

    public final String name;
    public final int strength;

    public Move(String name, int strength) {
        this.name = name;
        this.strength = strength;
    }
}
