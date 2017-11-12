package hundsfickerei.schafkopf.client.network;

import java.net.URISyntaxException;

public class ClientApp {

    public static void main(String[] args) throws InterruptedException {

        NetworkController networkController = new NetworkController();
        Thread.sleep(2000);
        networkController.sendToServer("1st message");
        networkController.sendToServer("2nd message");
        System.out.println("Done ...");



    }
}
