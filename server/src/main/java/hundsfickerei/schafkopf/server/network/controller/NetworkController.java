package hundsfickerei.schafkopf.server.network.controller;

import hundsfickerei.schafkopf.server.network.model.Client;
import hundsfickerei.schafkopf.server.network.model.ClientConnectionHandler;
import hundsfickerei.schafkopf.server.network.model.ClientMessageHandler;
import hundsfickerei.schafkopf.server.network.model.ClientStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.zeromq.ZContext;

import javax.annotation.PostConstruct;

@Controller
public class NetworkController {

    final static Logger LOG = LoggerFactory.getLogger(NetworkController.class);

    @Autowired
    ClientStore clientStore;


    @PostConstruct
    public void init() throws Exception {

        ZContext ctx = new ZContext();
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(ctx);
        ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler(ctx, clientStore);
        new Thread(clientConnectionHandler).start();

        Thread.sleep(5000);
        System.out.println("AFTER SLEEP");
        clientMessageHandler.broadcast("THIS IS A BROADCAST");
        System.out.println("Sending message to each client");
        for(Client client : clientStore.getClients()){
            clientMessageHandler.sendToClient(client.getIdentity(), "DEDICATED MSG FOR " + client.getPlayerNumber());
        }
    }


}
