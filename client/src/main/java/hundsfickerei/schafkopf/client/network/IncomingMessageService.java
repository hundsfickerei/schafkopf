package hundsfickerei.schafkopf.client.network;


import com.fasterxml.jackson.databind.ObjectMapper;
import hundsfickerei.schafkopf.network.model.GameStateMessage;
import hundsfickerei.schafkopf.network.model.NetStateMessage;

import java.io.IOException;
import java.util.logging.Logger;
import static java.util.logging.Level.*;

public class IncomingMessageService {

    private final static Logger LOG = Logger.getLogger(IncomingMessageService.class.getSimpleName());


    private static  ObjectMapper mapper = new ObjectMapper();

    public void processMessage(String msg){
        try {
            NetStateMessage netStateMessage = mapper.readValue(msg, NetStateMessage.class);
            switch (netStateMessage.getType()){
                case GAME:
                    GameStateMessage gsm = mapper.readValue(netStateMessage.getPayload(), GameStateMessage.class);
                    //TODO handle GameStateMessage
                    break;
                case ACK:
                    LOG.log(INFO, "Successfully connected to server!");
                    break;
                case INFO:
                    LOG.log(INFO,"Message is of type INFO : " + netStateMessage.getPayload());
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
