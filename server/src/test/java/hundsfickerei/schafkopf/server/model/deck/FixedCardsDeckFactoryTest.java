package hundsfickerei.schafkopf.server.model.deck;

import hundsfickerei.schafkopf.server.model.Card;
import hundsfickerei.schafkopf.server.model.Rank;
import hundsfickerei.schafkopf.server.model.Suit;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class FixedCardsDeckFactoryTest {

    @Test
    public void testInputEqualsOutput() throws Exception {
        Card card1 = new Card(Suit.EICHEL, Rank.OBER);
        Card card2 = new Card(Suit.HERZ, Rank.ASS);
        Card card3 = new Card(Suit.GRAS, Rank.UNTER);

        DeckFactory deckFactory = new FixedCardsDeckFactory(card1, card2, card3);
        List<Card> cards = deckFactory.buildDeck();

        assertThat(cards, is(notNullValue()));
        assertThat(cards, hasSize(3));
        assertThat(cards.get(0), is(equalTo(card1)));
        assertThat(cards.get(1), is(equalTo(card2)));
        assertThat(cards.get(2), is(equalTo(card3)));
    }

}
