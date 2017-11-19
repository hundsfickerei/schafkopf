package hundsfickerei.schafkopf.network.model;

public class NetStateMessage {

    private Type type;
    private String payload;

    public enum Type {
        GAME, REGISTER, ACK, INFO
    }

    public NetStateMessage() { }

    public NetStateMessage(Type type) {
        this.type = type;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public Type getType() {
        return type;
    }
}
