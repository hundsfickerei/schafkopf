package hundsfickerei.schafkopf.server.network.controller;

import hundsfickerei.schafkopf.server.network.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class NetworkController {

    final static Logger LOG = LoggerFactory.getLogger(NetworkController.class);

    @Autowired
    ClientStore clientStore;

    @Autowired
    ZContextBean ctx;

    @Autowired
    OutgoingMessageService outgoingMessageService;

    @Autowired
    IncomingMessageService incomingMessageService;


    @PostConstruct
    public void init() throws Exception {

        ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler(ctx.getContext(), incomingMessageService);
        new Thread(clientConnectionHandler).start();

        //PLAYGROUND  :)
        Thread.sleep(5000);

        for(Client client : clientStore.getClients()){
            outgoingMessageService.sendInfoMessageToClient(client.getIdentity(), "DEDICATED MSG FOR " + client.getPlayerNumber());
        }

        outgoingMessageService.broadcastInfoMessage("This goes to all clients");
    }


}
