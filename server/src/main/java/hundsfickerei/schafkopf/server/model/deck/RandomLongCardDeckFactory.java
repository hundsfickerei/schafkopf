package hundsfickerei.schafkopf.server.model.deck;

import hundsfickerei.schafkopf.server.model.Card;
import hundsfickerei.schafkopf.server.model.Rank;
import hundsfickerei.schafkopf.server.model.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomLongCardDeckFactory implements DeckFactory {

    /**
     * @return a deck of long card game containing 32 unique cards in random order
     */
    @Override
    public List<Card> buildDeck() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);
        return cards;
    }

}
