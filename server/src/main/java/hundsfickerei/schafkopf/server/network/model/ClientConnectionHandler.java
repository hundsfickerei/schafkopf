package hundsfickerei.schafkopf.server.network.model;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class ClientConnectionHandler implements Runnable  {

    private final ClientStore clientStore;
    private ZContext ctx;

    public ClientConnectionHandler(ZContext ctx, ClientStore clientStore) {
        this.ctx = ctx;
        this.clientStore = clientStore;
    }

    public void run() {

        //  Frontend socket talks to clients over TCP
        ZMQ.Socket frontend = ctx.createSocket(ZMQ.ROUTER);
        frontend.bind("tcp://*:5570");

        //  Backend socket talks to workers over inproc
        ZMQ.Socket backend = ctx.createSocket(ZMQ.DEALER);
        backend.bind("inproc://backend");


        //  Launch pool of worker threads, precise number is not critical
        for (int threadNbr = 0; threadNbr < 5; threadNbr++){
            new Thread(new ClientConnectionWorker(ctx, clientStore)).start();
        }

        //  Connect backend to frontend via a proxy
        ZMQ.proxy(frontend, backend, null);
        ctx.destroy();
    }


}