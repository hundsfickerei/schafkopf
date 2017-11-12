package hundsfickerei.schafkopf.client.network;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.util.UUID;
import java.util.Random;

public class NetworkController  {

    private static Random rand = new Random(System.nanoTime());

    private ZMQ.Socket upChannel;

    private String identity;

    public NetworkController() {
        this.identity = UUID.randomUUID().toString();
        initialize();
    }

    private void initialize() {
        ZContext ctx = new ZContext();
        upChannel = ctx.createSocket(ZMQ.DEALER);
        upChannel.setIdentity(identity.getBytes());
        upChannel.connect("tcp://localhost:5570");

        ServerConnectionWorker worker = new ServerConnectionWorker(ctx, upChannel, identity);
        new Thread(worker).start();

    }

    public void sendToServer(String msg){
        upChannel.send(msg,0);
    }


}