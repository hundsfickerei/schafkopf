package hundsfickerei.schafkopf.model;

import java.util.Comparator;

public class RankComparator implements Comparator<Rank> {

    @Override
    public int compare(Rank r1, Rank r2) {
        return Integer.compare(r1.getStrength(), r2.getStrength());
    }

}
