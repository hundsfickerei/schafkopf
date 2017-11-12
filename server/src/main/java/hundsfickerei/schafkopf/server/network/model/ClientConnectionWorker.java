package hundsfickerei.schafkopf.server.network.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.Random;

public class ClientConnectionWorker implements Runnable {
    private final ClientStore clientStore;
    private ZContext ctx;
    private static final Logger LOG = LoggerFactory.getLogger(ClientConnectionWorker.class);


    public ClientConnectionWorker(ZContext ctx, ClientStore clientStore) {
        this.ctx = ctx;
        this.clientStore = clientStore;
    }

    public void run() {
        System.out.println("ClientConnectionWorker running ...");
        ZMQ.Socket worker = ctx.createSocket(ZMQ.DEALER);
        worker.connect("inproc://backend");

        while (!Thread.currentThread().isInterrupted()) {
            //  The DEALER socket gives us the address envelope and message
            ZMsg msg = ZMsg.recvMsg(worker);
            ZFrame address = msg.pop();
            ZFrame content = msg.pop();
            LOG.debug("Received message from address: " + address.toString());
            LOG.debug("Payload: " + content.toString());


            clientStore.addClient(address.toString());

            msg.destroy();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            address.destroy();
            content.destroy();
        }
        ctx.destroy();
    }
}

