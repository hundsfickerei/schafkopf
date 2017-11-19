package hundsfickerei.schafkopf.client.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hundsfickerei.schafkopf.network.model.GameStateMessage;
import hundsfickerei.schafkopf.network.model.NetStateMessage;

import java.util.logging.Logger;

import static java.util.logging.Level.*;

public class OutgoingMessageHandler {

    private final static Logger LOG = Logger.getLogger(IncomingMessageHandler.class.getSimpleName());

    private ObjectMapper mapper = new ObjectMapper();

    private NetworkController controller;

    public OutgoingMessageHandler(NetworkController controller) {
        this.controller = controller;
    }

    public void registerAtServer(){
        LOG.log(INFO,"Registering at server ...");
        NetStateMessage netStateMessage = new NetStateMessage(NetStateMessage.Type.REGISTER);
        try {
            controller.sendMessageToServer(mapper.writeValueAsString(netStateMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendGameMessageToServer(GameStateMessage msg){
        NetStateMessage netStateMessage = new NetStateMessage(NetStateMessage.Type.GAME);
        try {
            netStateMessage.setPayload(mapper.writeValueAsString(msg));
            controller.sendMessageToServer(mapper.writeValueAsString(netStateMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
