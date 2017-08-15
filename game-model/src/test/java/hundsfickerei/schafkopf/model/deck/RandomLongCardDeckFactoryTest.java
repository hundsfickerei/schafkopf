package hundsfickerei.schafkopf.model.deck;

import hundsfickerei.schafkopf.model.Card;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class RandomLongCardDeckFactoryTest {

    @Test
    public void testOutputSize() throws Exception {
        DeckFactory deckFactory = new RandomLongCardDeckFactory();
        List<Card> cards = deckFactory.buildDeck();

        assertThat(cards, hasSize(32));
    }

}
