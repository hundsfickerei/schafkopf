package hundsfickerei.schafkopf.server.network.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.zeromq.ZMQ;


//@Controller
//TODO make multi-threaded
//TODO use pub-sub
public class NetworkController {

    final static Logger LOG = LoggerFactory.getLogger(NetworkController.class);
    final static String ADDRESS = "localhost";
    final static String PORT = "5555";

    private ZMQ.Socket socket;

    public NetworkController() throws InterruptedException {
        LOG.debug("Starting game server ...");
        this.startGameServer();
    }

    public void startGameServer() throws InterruptedException {

        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to clients
        socket = context.socket(ZMQ.REP);
        socket.bind("tcp://" + ADDRESS + ":" + PORT);
        LOG.debug("Opened ZeroMQ socket!");

        while (!Thread.currentThread().isInterrupted()) {
            // Wait for next request from the client
            byte[] request = socket.recv(0);
            String msg = new String(request);
            LOG.debug("Received new request from client!");
            LOG.debug("Payload is: " + msg);
            handleRequest(msg);
        }
        socket.close();
        context.term();
    }


    private void handleRequest(String request){
        //TODO parse game state message
        //TODO implement logic
        sendResponse("This is a very smart response!");
    }


    public void sendResponse(String response){
        //TODO should take GameStateMessage as parameter
        LOG.debug("Sending response to client!");
        socket.send(response.getBytes(), 0);
    }



}
