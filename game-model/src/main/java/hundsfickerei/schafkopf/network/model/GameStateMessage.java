package hundsfickerei.schafkopf.network.model;

import hundsfickerei.schafkopf.model.Card;
import hundsfickerei.schafkopf.model.GameMode;

import java.util.List;

/**
 * GameStateMessage class is used to communicate the current game state
 * between client and server.
 */
public class GameStateMessage {

   private int clientId;
   private boolean clientIsNextToMove;
   private GameMode gameMode;
   private List<Card> hand;
   private List<Card> cardsOnTable;
   private boolean trickWonByClient;
   private int pointsFromTrick;

   public int getClientId() {
      return clientId;
   }

   public void setClientId(int clientId) {
      this.clientId = clientId;
   }

   public boolean isClientIsNextToMove() {
      return clientIsNextToMove;
   }

   public void setClientIsNextToMove(boolean clientIsNextToMove) {
      this.clientIsNextToMove = clientIsNextToMove;
   }

   public GameMode getGameMode() {
      return gameMode;
   }

   public void setGameMode(GameMode gameMode) {
      this.gameMode = gameMode;
   }

   public List<Card> getHand() {
      return hand;
   }

   public void setHand(List<Card> hand) {
      this.hand = hand;
   }

   public List<Card> getCardsOnTable() {
      return cardsOnTable;
   }

   public void setCardsOnTable(List<Card> cardsOnTable) {
      this.cardsOnTable = cardsOnTable;
   }

   public boolean isTrickWonByClient() {
      return trickWonByClient;
   }

   public void setTrickWonByClient(boolean trickWonByClient) {
      this.trickWonByClient = trickWonByClient;
   }

   public int getPointsFromTrick() {
      return pointsFromTrick;
   }

   public void setPointsFromTrick(int pointsFromTrick) {
      this.pointsFromTrick = pointsFromTrick;
   }
}
