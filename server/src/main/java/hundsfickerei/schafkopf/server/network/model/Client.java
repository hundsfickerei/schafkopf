package hundsfickerei.schafkopf.server.network.model;

public class Client {

    private static int count = 0;
    private String identity;
    private int playerNumber;

    public Client(String identity, int playerNumber) {
        this.identity = identity;
        this.playerNumber = playerNumber;
    }

    public String getIdentity() {
        return identity;
    }

    public static Client build(String id){
        return new Client(id, count++);
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
