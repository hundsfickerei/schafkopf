package hundsfickerei.schafkopf.client.network;


import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import java.util.UUID;
import java.util.Random;
import java.util.logging.Logger;
import static java.util.logging.Level.*;


public class NetworkController  {

    private final static Logger LOG = Logger.getLogger(IncomingMessageService.class.getSimpleName());


    private static Random rand = new Random(System.nanoTime());

    private ZMQ.Socket upChannel;

    private String identity;

    private IncomingMessageService incomingMessageService;
    private OutgoingMessageService outgoingMessageService;

    public NetworkController() {
        this.identity = UUID.randomUUID().toString();
        this.incomingMessageService = new IncomingMessageService();
        this.outgoingMessageService = new OutgoingMessageService(this);
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

    public synchronized void receiveMessageFromServer(String msg){
        LOG.log(INFO,"-------------------");
        LOG.log(INFO, "Received new message from server!");
        LOG.log(INFO,"Payload: " + msg);
        incomingMessageService.processMessage(msg);
    }

    public ZMQ.Socket getUpChannel() {
        return upChannel;
    }

    public String getIdentity() {
        return identity;
    }

    public OutgoingMessageService getOutgoingMessageService() {
        return outgoingMessageService;
    }
}