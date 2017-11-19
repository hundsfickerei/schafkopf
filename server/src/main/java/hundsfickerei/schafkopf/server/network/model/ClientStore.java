package hundsfickerei.schafkopf.server.network.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClientStore {

    private List<Client> clients = new ArrayList<>();
    private static final Logger LOG = LoggerFactory.getLogger(ClientStore.class);


    public synchronized void addClient(String id){
        Optional<Client> any = clients.stream()
                .filter(c -> Objects.equals(c.getIdentity(), id))
                .findAny();

        if( ! any.isPresent()){
            clients.add(Client.build(id));
            LOG.debug("Registered client " + id + "!");
        } else {
            LOG.debug("Client is already registered!");
        }
    }

    public List<Client> getClients() {
        return clients;
    }
}
