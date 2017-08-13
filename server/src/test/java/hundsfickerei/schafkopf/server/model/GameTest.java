package hundsfickerei.schafkopf.server.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
        int sum = 0;
        for (Player player : players) {
            sum = sum + player.getPoints();
        }
        assertThat(sum, is(equalTo(120)));
    }

}
