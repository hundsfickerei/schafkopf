package hundsfickerei.schafkopf.server.network.model;

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


}
