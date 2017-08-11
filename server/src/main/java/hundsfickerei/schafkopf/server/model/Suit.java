package hundsfickerei.schafkopf.server.model;

public enum Suit {

    EICHEL(4),
    GRAS(3),
    HERZ(2),
    SCHELLE(1);

    private int strength;

    Suit(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
