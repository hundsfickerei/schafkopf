package hundsfickerei.schafkopf.client.network;

import hundsfickerei.schafkopf.model.GameMode;
import hundsfickerei.schafkopf.network.model.GameStateMessage;

public class ClientApp {

    public static void main(String[] args) throws InterruptedException {

        NetworkController networkController = new NetworkController();
        OutgoingMessageHandler outgoingMessageHandler = networkController.getOutgoingMessageHandler();
        outgoingMessageHandler.registerAtServer();
        Thread.sleep(5000);


        GameStateMessage gsm = new GameStateMessage();
        gsm.setGameMode(GameMode.HERZ_SOLO);
        outgoingMessageHandler.sendGameMessageToServer(gsm);


        System.out.println("Done ...");



    }
}
