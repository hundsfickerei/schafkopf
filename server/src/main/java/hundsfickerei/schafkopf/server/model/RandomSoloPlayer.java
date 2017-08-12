package hundsfickerei.schafkopf.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSoloPlayer implements Player {

    private final Random random = new Random();
    private final List<Card> hand = new ArrayList<>();
    private final Game game;
    private int points = 0;

    public RandomSoloPlayer(Game game) {
        this.game = game;
    }

    @Override
    public GameMode getSuggestedGameMode() {
        if (random.nextInt(2) == 0) {
            List<GameMode> modes = new ArrayList<>();
            modes.add(GameMode.EICHEL_SOLO);
            modes.add(GameMode.GRAS_SOLO);
            modes.add(GameMode.HERZ_SOLO);
            modes.add(GameMode.SCHELLEN_SOLO);
            return modes.get(random.nextInt(modes.size()));
        }
        return GameMode.NONE;
    }

    @Override
    public void receiveHand(List<Card> hand) {
        this.hand.addAll(hand);
    }

    @Override
    public Card playCard(List<Card> cardsOnTable) {
        List<Card> playableCards = new ArrayList<>();
        if (cardsOnTable.size() == 0) {
            playableCards.addAll(hand);
        } else {
            for (Card card : hand) {
                Card firstCard = cardsOnTable.get(0);
                if (!(!game.isSameSuit(firstCard, card, game.getGameMode()) && game.hasSameSuit(hand, firstCard, game.getGameMode()))) {
                    playableCards.add(card);
                }
            }
        }
        Card card = playableCards.get(random.nextInt(playableCards.size()));
        hand.remove(card);
        return card;
    }

    @Override
    public List<Card> getRemainingCards() {
        return hand;
    }

    @Override
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    @Override
    public int getPoints() {
        return points;
    }
}
