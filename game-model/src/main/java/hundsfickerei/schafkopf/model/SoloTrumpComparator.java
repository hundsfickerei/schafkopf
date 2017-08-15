package hundsfickerei.schafkopf.model;

import java.util.Comparator;

public class SoloTrumpComparator implements Comparator<Card> {

    private SuitComparator suitComparator = new SuitComparator();
    private RankComparator rankComparator = new RankComparator();

    @Override
    public int compare(Card c1, Card c2) {
        if (c1.getRank().equals(Rank.OBER) && c2.getRank().equals(Rank.OBER)) {
            return suitComparator.compare(c1.getSuit(), c2.getSuit());
        }
        if (c1.getRank().equals(Rank.OBER) || c2.getRank().equals(Rank.OBER)) {
            return c1.getRank().equals(Rank.OBER) ? 1 : -1;
        }
        // no OBER
        if (c1.getRank().equals(Rank.UNTER) && c2.getRank().equals(Rank.UNTER)) {
            return suitComparator.compare(c1.getSuit(), c2.getSuit());
        }
        if (c1.getRank().equals(Rank.UNTER) || c2.getRank().equals(Rank.UNTER)) {
            return c1.getRank().equals(Rank.UNTER) ? 1 : -1;
        }
        // no UNTER
        return rankComparator.compare(c1.getRank(), c2.getRank());
    }
}
