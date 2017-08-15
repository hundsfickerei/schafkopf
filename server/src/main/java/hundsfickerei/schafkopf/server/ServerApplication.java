package hundsfickerei.schafkopf.server;

import hundsfickerei.schafkopf.server.network.controller.NetworkController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);

		NetworkController netController = new NetworkController();
		try {
			netController.startGameServer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
