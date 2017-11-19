package hundsfickerei.schafkopf.server.network.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hundsfickerei.schafkopf.network.model.GameStateMessage;
import hundsfickerei.schafkopf.network.model.NetStateMessage;
import hundsfickerei.schafkopf.server.network.model.ZContextBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import static hundsfickerei.schafkopf.network.model.NetStateMessage.Type.*;
import javax.annotation.PostConstruct;

/**
 * OutgoingMessageService offers message sending functionality
 * for both individual recipients, as well as braodcasts
 */
@Component
public class OutgoingMessageService {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ZContextBean ctx;
    ZMQ.Socket publisher;

    @PostConstruct
    public void init() throws InterruptedException {
        publisher = ctx.getContext().createSocket(ZMQ.PUB);
        publisher.bind("tcp://*:5500");

    }

    public void broadcastGameStateMessage(GameStateMessage gsm){
        NetStateMessage netStateMessage = new NetStateMessage(GAME);
        try {
            netStateMessage.setPayload(mapper.writeValueAsString(gsm));
            broadcast(mapper.writeValueAsString(netStateMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void broadcastInfoMessage(String infoMessage){
        NetStateMessage netStateMessage = new NetStateMessage(INFO);
        try {
            netStateMessage.setPayload(infoMessage);
            broadcast(mapper.writeValueAsString(netStateMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendGameStateMessageToClient(String id, GameStateMessage gsm){
        NetStateMessage netStateMessage = new NetStateMessage(GAME);
        try {
            netStateMessage.setPayload(mapper.writeValueAsString(gsm));
            sendToClient(id, mapper.writeValueAsString(netStateMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendInfoMessageToClient(String id, String infoMessage){
        NetStateMessage netStateMessage = new NetStateMessage(INFO);
        try {
            netStateMessage.setPayload(infoMessage);
            sendToClient(id, mapper.writeValueAsString(netStateMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendAckMessageToClient(String id){
        NetStateMessage netStateMessage = new NetStateMessage(ACK);
        try {
            sendToClient(id, mapper.writeValueAsString(netStateMessage));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    private void broadcast(String message){
        publisher.sendMore ("GAME");
        publisher.send (message);
    }

    private void sendToClient(String identity, String message){
        publisher.sendMore (identity);
        publisher.send(message);
    }

}
