package hundsfickerei.schafkopf.client.network;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.io.IOException;
import java.nio.channels.Selector;

public class ServerConnectionWorker implements Runnable{

    private NetworkController controller;
    private ZMQ.Socket downChannel;
    private ZContext ctx;

    public ServerConnectionWorker(ZContext ctx, NetworkController controller) {
        this.ctx = ctx;
        this.controller = controller;
    }

    @Override
    public void run() {

        //(1) subscribe for server's broadcast channel (PUB SUB)
        downChannel = ctx.createSocket(ZMQ.SUB);
        downChannel.connect("tcp://localhost:5500");
        downChannel.subscribe("GAME".getBytes());
        //topic with own identity is used to receive dedicated messages
        downChannel.subscribe(controller.getIdentity().getBytes());

        ZMQ.PollItem[] items = new ZMQ.PollItem[] { new ZMQ.PollItem(controller.getUpChannel(), ZMQ.Poller.POLLIN),
                new ZMQ.PollItem(downChannel, ZMQ.Poller.POLLIN)};

        int requestNbr = 0;
        Selector selector = buildSelector();
        while (!Thread.currentThread().isInterrupted()) {
            //  Tick once per second, pulling in arriving messages
            for (int centitick = 0; centitick < 100; centitick++) {
                ZMQ.poll(selector, items, 10);
                if (items[0].isReadable()) {
                    ZMsg msg = ZMsg.recvMsg(controller.getUpChannel());
                    //msg.getLast().print(controller.getIdentity());
                    controller.receiveMessageFromServer(msg.getLast().toString());
                    msg.destroy();
                }
                if(items[1].isReadable()){
                    ZMsg subMsg = ZMsg.recvMsg(downChannel);
                    //subMsg.getLast().print(controller.getIdentity());
                    controller.receiveMessageFromServer(subMsg.getLast().toString());
                    subMsg.destroy();
                }
            }
            //upChannel.send(String.format("Message from Client #%d", ++requestNbr), 0);
        }
        ctx.destroy();
    }

    private Selector buildSelector(){
        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selector;
    }


}
