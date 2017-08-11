package hundsfickerei.schafkopf.server.model;

public enum Rank {

    ASS(8, 11),
    ZEHN(7, 10),
    KOENIG(6, 4),
    OBER(5, 3),
    UNTER(4, 2),
    NEUN(3, 0),
    ACHT(2, 0),
    SIEBEN(1, 0);

    private final int strength;
    private final int points;

    Rank(int strength, int points) {
        this.strength = strength;
        this.points = points;
    }

    public int getStrength() {
        return strength;
    }

    public int getPoints() {
        return points;
    }
}
