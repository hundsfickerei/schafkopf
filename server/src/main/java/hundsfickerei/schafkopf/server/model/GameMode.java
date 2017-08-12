package hundsfickerei.schafkopf.server.model;

public enum GameMode {

    EICHEL_SOLO(2, Type.SOLO, Suit.EICHEL),
    GRAS_SOLO(2, Type.SOLO, Suit.GRAS),
    HERZ_SOLO(2, Type.SOLO, Suit.HERZ),
    SCHELLEN_SOLO(2, Type.SOLO, Suit.SCHELLE),
    NONE(1, null, null);

    public enum Type {
        SOLO;
    }

    private final int strength;
    private final Type type;
    private final Suit suit;

    GameMode(int strength, Type type, Suit suit) {
        this.strength = strength;
        this.type = type;
        this.suit = suit;
    }

    public int getStrength() {
        return strength;
    }

    public Type getType() {
        return type;
    }

    public Suit getSuit() {
        return suit;
    }
}
