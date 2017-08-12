package hundsfickerei.schafkopf.server.model;

import java.util.Comparator;

public class SuitComparator implements Comparator<Suit> {
    @Override
    public int compare(Suit s1, Suit s2) {
        return Integer.compare(s1.getStrength(), s2.getStrength());
    }
}
