package hundsfickerei.schafkopf.model;

import java.util.List;

public interface Player {

    GameMode getSuggestedGameMode();

    void receiveHand(List<Card> hand);

    Card playCard(List<Card> cardsOnTable);

    List<Card> getRemainingCards();

    void addPoints(int points);

    int getPoints();
}
