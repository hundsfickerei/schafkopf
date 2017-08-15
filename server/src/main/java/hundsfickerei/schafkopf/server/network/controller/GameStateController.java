package hundsfickerei.schafkopf.server.network.controller;

import hundsfickerei.schafkopf.server.network.model.Client;
import hundsfickerei.schafkopf.server.network.model.GameStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class GameStateController {

    final static Logger LOG = LoggerFactory.getLogger(NetworkController.class);

    private List<Client> clients;
    private GameStage gameStage;

    //TODO trigger game after 4 players joined
    public boolean addClientToGame(Client client){
        if(clients.size() < 4){
            clients.add(client);
            LOG.debug("Added client " + client.getIdentifier() + " to game!");
            return true;
        } else {
            LOG.debug("Cannot add client because game is already full!");
            return false;
        }
    }
}
