package netty;

public class Message {

    //��Ϣ����
    private byte type;

    //��Ϣ����
    private int length;

    //��Ϣ��
    private String msgBody;

    public Message(byte type, int length, String msgBody) {
        this.type = type;
        this.length = length;
        this.msgBody = msgBody;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
}
