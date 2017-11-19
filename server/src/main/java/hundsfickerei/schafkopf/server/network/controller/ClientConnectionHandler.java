package hundsfickerei.schafkopf.server.network.controller;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * ClientConnectionHandler opens all necessary sockets for the
 * processing of incoming client requests and also spawns
 * a number of ClientConnectionHandlers to deal with the request asynchronously
 */
public class ClientConnectionHandler implements Runnable  {

    private ZContext ctx;
    private IncomingMessageService handler;

    public ClientConnectionHandler(ZContext ctx, IncomingMessageService handler) {
        this.ctx = ctx;
        this.handler = handler;
    }

    public void run() {

        //Frontend socket talks to clients over TCP
        ZMQ.Socket frontend = ctx.createSocket(ZMQ.ROUTER);
        frontend.bind("tcp://*:5570");

        //Backend socket talks to ClientConnectionWorkers over inproc
        ZMQ.Socket backend = ctx.createSocket(ZMQ.DEALER);
        backend.bind("inproc://backend");


        //Launch pool of worker threads to handle incoming client messages
        for (int threadNbr = 0; threadNbr < 5; threadNbr++){
            new Thread(new ClientConnectionWorker(ctx, handler)).start();
        }

        //  Connect backend to frontend via a proxy
        ZMQ.proxy(frontend, backend, null);
        ctx.destroy();
    }


}