package hundsfickerei.schafkopf.server.network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hundsfickerei.schafkopf.network.model.GameStateMessage;
import hundsfickerei.schafkopf.network.model.NetStateMessage;
import hundsfickerei.schafkopf.server.network.model.ClientStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * IncomingMessageServices receives client requests and parses them
 * according to the message type
 * see NetStateMessage.Type for available message types
 */
@Component
public class IncomingMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientConnectionWorker.class);


    @Autowired
    ClientStore clientStore;

    @Autowired
    OutgoingMessageService outgoingMessageService;

    private ObjectMapper mapper = new ObjectMapper();


    public synchronized void receiveMessage(String id, String msg) throws JsonProcessingException {
        NetStateMessage netStateMessage;
        try {
            netStateMessage = mapper.readValue(msg, NetStateMessage.class);
            switch (netStateMessage.getType()) {
                case GAME:
                    GameStateMessage gsm = mapper.readValue(netStateMessage.getPayload(), GameStateMessage.class);
                    LOG.debug("Message from client " + id + " is of type GAME");
                    //TODO handle GAME state message
                    break;
                case REGISTER:
                    LOG.debug("Message from client " + id + " is of type REGISTER ");
                    registerClient(id);
                    break;
            }
        } catch (IOException e) {
            String infoMsg = "Your last message could not be processed by the game server!";
            outgoingMessageService.sendInfoMessageToClient(id, infoMsg);
            e.printStackTrace();
        }


    }

    private void registerClient(String id) throws JsonProcessingException {
        clientStore.addClient(id);
        LOG.debug("Sending ACK to client");
        outgoingMessageService.sendAckMessageToClient(id);
    }


}
