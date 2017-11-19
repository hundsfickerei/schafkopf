package hundsfickerei.schafkopf.server.network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

/**
 * Network thread that receives requests asynchronously from clients via DEALER socket
 * and forwards them to the IncomingMessageService
 */
public class ClientConnectionWorker implements Runnable {
    private IncomingMessageService incomingMessageService;
    private ZContext ctx;
    private static final Logger LOG = LoggerFactory.getLogger(ClientConnectionWorker.class);


    public ClientConnectionWorker(ZContext ctx, IncomingMessageService incomingMessageService) {
        this.ctx = ctx;
        this.incomingMessageService = incomingMessageService;
    }

    public void run() {
        LOG.debug("Spawned new ClientConnectionWorker!");
        ZMQ.Socket worker = ctx.createSocket(ZMQ.DEALER);
        worker.connect("inproc://backend");

        while (!Thread.currentThread().isInterrupted()) {
            //  The DEALER socket gives us the address envelope and message
            ZMsg msg = ZMsg.recvMsg(worker);
            ZFrame address = msg.pop();
            ZFrame content = msg.pop();
            LOG.debug("---------------------");
            LOG.debug("Received message from address: " + address.toString());
            LOG.debug("Payload: " + content.toString());

            try {
                incomingMessageService.receiveMessage(address.toString(), content.toString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

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

