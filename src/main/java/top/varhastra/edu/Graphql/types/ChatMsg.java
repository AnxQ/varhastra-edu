package top.varhastra.edu.Graphql.types;

public class ChatMsg {
    String type;
    String msg;
    String senderId;

    public ChatMsg(String type, String msg, String senderId) {
        this.type = type;
        this.msg = msg;
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
