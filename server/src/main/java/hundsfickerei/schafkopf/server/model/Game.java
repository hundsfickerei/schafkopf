package hundsfickerei.schafkopf.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private GameMode gameMode = GameMode.NONE;
    private int gameModePlayer = -1;
    private List<Player> players;
    private int nextPlayer = 0;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void init(List<Player> players) {
        if (players == null || players.size() != 4) {
            throw new IllegalArgumentException();
        }
        for (Player player : players) {
            if (player == null) {
                throw  new IllegalArgumentException();
            }
        }
        this.players = players;
    }

    public void start() {
        if (players == null) {
            throw new IllegalStateException();
        }
        decideGameMode();
        if (gameMode.equals(GameMode.NONE)) {
            System.out.println("Game mode is none; no game.");
            return;
        }

        List<Card> cards = shuffleCards();
        dealCards(cards);

        for (int i = 0; i < 8; i++) {
            List<Card> cardsOnTable = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Player currentPlayer = players.get(nextPlayer);

                Card playedCard = currentPlayer.playCard(cardsOnTable);
                List<Card> remainingCards = currentPlayer.getRemainingCards();

                verifyCard(cardsOnTable, playedCard, remainingCards);
                cardsOnTable.add(playedCard);

                nextPlayer = (nextPlayer + 1) % 4;
            }

            nextPlayer = getTrickWinner(cardsOnTable, gameMode);
            players.get(nextPlayer).addPoints(getPoints(cardsOnTable));
        }
    }

    private int getPoints(List<Card> cards){
        int sum = 0;
        for (Card card : cards) {
            sum = sum + card.getRank().getPoints();
        }
        return sum;
    }

    private void verifyCard(List<Card> cardsOnTable, Card card, List<Card> remainingCards) {
        if (cardsOnTable.isEmpty()) {
            return;
        }
        Card firstCard = cardsOnTable.get(0);
        if (!isSameSuit(firstCard, card, gameMode) && hasSameSuit(remainingCards, firstCard, gameMode)) {
            throw new RuntimeException();
        }
    }

    private int getTrickWinner(List<Card> cards, GameMode gameMode) {
        SoloTrumpComparator soloTrumpComparator = new SoloTrumpComparator();
        RankComparator rankComparator = new RankComparator();
        int pos = -1;
        for (Card card : cards) {
            if (isTrump(card, gameMode)) {
                if (pos == -1 ||
                    !isTrump(cards.get(pos), gameMode) ||
                    soloTrumpComparator.compare(card, cards.get(pos)) > 0) {
                    pos = cards.indexOf(card);
                }
            } else {
                if (pos == -1 ||
                    !isTrump(cards.get(pos), gameMode) &&
                    isSameSuit(card, cards.get(pos), gameMode) &&
                    rankComparator.compare(card.getRank(), cards.get(pos).getRank()) > 0) {
                    pos = cards.indexOf(card);
                }
            }
        }
        return pos;
    }

    public boolean isTrump(Card card, GameMode gameMode) {
        if (gameMode.getType().equals(GameMode.Type.SOLO)) {
            return gameMode.getSuit().equals(card.getSuit()) ||
                    card.getRank().equals(Rank.OBER) ||
                    card.getRank().equals(Rank.UNTER);
        }
        throw new IllegalArgumentException("unknown game mode");
    }

    /**
     * this method considers trump as a suit
     *
     * @param card1
     * @param card2
     * @param gameMode
     * @return
     */
    public boolean isSameSuit(Card card1, Card card2, GameMode gameMode) {
        if (isTrump(card1, gameMode) && isTrump(card2, gameMode)) {
            return true;
        }
        if (isTrump(card1, gameMode) || isTrump(card2, gameMode)) {
            return false;
        }
        return card1.getSuit().equals(card2.getSuit());
    }

    public boolean hasSameSuit(List<Card> cards, Card card, GameMode gameMode) {
        for (Card c : cards) {
            if (isSameSuit(card, c, gameMode)) {
                return true;
            }
        }
        return false;
    }

    private void dealCards(List<Card> cards) {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                List<Card> hand = cards.subList(0, 4);
                player.receiveHand(new ArrayList<>(hand));
                hand.clear();
            }
        }
    }

    private List<Card> shuffleCards() {
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

    private void decideGameMode() {
        for (Player player : players) {
            GameMode suggestedGameMode = player.getSuggestedGameMode();
            if (suggestedGameMode.getStrength() > gameMode.getStrength()) {
                gameMode = suggestedGameMode;
                gameModePlayer = nextPlayer;
                nextPlayer = (nextPlayer + 1) % 4;
            }
        }
    }

}
