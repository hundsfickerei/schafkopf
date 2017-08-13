package hundsfickerei.schafkopf.server.model.deck;

import hundsfickerei.schafkopf.server.model.Card;

import java.util.List;

public interface DeckFactory {

    /**
     * build a deck of cards, subclasses determine the amount and order of the cards in the deck
     * @return a list of cards
     */
    List<Card> buildDeck();

}
