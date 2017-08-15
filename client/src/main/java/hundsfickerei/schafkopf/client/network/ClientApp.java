package hundsfickerei.schafkopf.client.network;

import java.net.URISyntaxException;

public class ClientApp {

    public static void main(String[] args) {

        NetworkController networkController = new NetworkController();

            networkController.connectToServer();
            System.out.println("Done ...");



    }
}
