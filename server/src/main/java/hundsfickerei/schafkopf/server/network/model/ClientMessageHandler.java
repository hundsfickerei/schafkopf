package hundsfickerei.schafkopf.server.network.model;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ClientMessageHandler {

    private final ZContext ctx;
    ZMQ.Socket publisher;

    public ClientMessageHandler(ZContext ctx) throws InterruptedException {
        this.ctx = ctx;
        publisher = ctx.createSocket(ZMQ.PUB);
        publisher.bind("tcp://*:5500");

        Thread.sleep(5000);
        // Write two messages, each with an envelope and content
        publisher.sendMore ("GAME");
        publisher.send ("TEST BROADCAST");
        publisher.sendMore ("B");
        publisher.send("We would not like to see this");
}

    public void broadcast(String message){
        publisher.sendMore ("GAME");
        publisher.send (message);
    }

    public void sendToClient(String identity, String message){
        publisher.sendMore (identity);
        publisher.send(message);
    }

}
