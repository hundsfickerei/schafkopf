package hundsfickerei.schafkopf.server.model;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Configuration
    public static class TestConfig {
        @Bean
        @Scope("prototype")
        public Game game() {
            return new Game();
        }
        @Bean
        @Scope("prototype")
        public Player player(Game game) {
            return new RandomSoloPlayer(game);
        }
    }

    @Test
    public void testRandomSoloPlayerGame() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
        Game game = ctx.getBean(Game.class);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(ctx.getBean(Player.class, game));
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
