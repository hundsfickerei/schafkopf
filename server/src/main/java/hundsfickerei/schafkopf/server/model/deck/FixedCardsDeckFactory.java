package hundsfickerei.schafkopf.server.model.deck;

import hundsfickerei.schafkopf.server.model.Card;

import java.util.ArrayList;
import java.util.List;

public class FixedCardsDeckFactory implements DeckFactory {

    private List<Card> cards = new ArrayList<>();

    public FixedCardsDeckFactory(Card... cards) {
        for (Card card : cards) {
            this.cards.add(card);
        }
    }

    /**
     * @return a deck of fixed cards as passed to the constructor
     */
    @Override
    public List<Card> buildDeck() {
        return cards;
    }

}
