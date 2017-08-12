package hundsfickerei.schafkopf.server.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

    @Test
    public void testRandomSoloPlayerGame() {
        Game game = new Game();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new RandomSoloPlayer(game));
        }
        game.init(players);
        game.start();
        System.out.println("Points:");
        for (Player player : players) {
            System.out.println(player.hashCode() + " " + player.getPoints());
        }

    }

}
