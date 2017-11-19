package hundsfickerei.schafkopf.client.network;


import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.util.UUID;
import java.util.Random;
import java.util.logging.Logger;
import static java.util.logging.Level.*;


public class NetworkController  {

    private final static Logger LOG = Logger.getLogger(IncomingMessageHandler.class.getSimpleName());


    private static Random rand = new Random(System.nanoTime());

    private ZMQ.Socket upChannel;

    private String identity;

    private IncomingMessageHandler incomingMessageHandler;
    private OutgoingMessageHandler outgoingMessageHandler;

    public NetworkController() {
        this.identity = UUID.randomUUID().toString();
        this.incomingMessageHandler = new IncomingMessageHandler();
        this.outgoingMessageHandler = new OutgoingMessageHandler(this);
        initialize();
    }

    private void initialize() {
        ZContext ctx = new ZContext();
        upChannel = ctx.createSocket(ZMQ.DEALER);
        upChannel.setIdentity(identity.getBytes());
        upChannel.connect("tcp://localhost:5570");

        ServerConnectionWorker worker = new ServerConnectionWorker(ctx, this);
        new Thread(worker).start();

    }

    public void sendMessageToServer(String msg){
        upChannel.send(msg,0);
    }

    public void receiveMessageFromServer(String msg){
        LOG.log(INFO,"-------------------");
        LOG.log(INFO, "Received new message from server!");
        LOG.log(INFO,"Payload: " + msg);
        incomingMessageHandler.processMessage(msg);
    }

    public ZMQ.Socket getUpChannel() {
        return upChannel;
    }

    public String getIdentity() {
        return identity;
    }

    public OutgoingMessageHandler getOutgoingMessageHandler() {
        return outgoingMessageHandler;
    }
}