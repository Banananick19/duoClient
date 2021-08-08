package banana.duo.Common;

public class Message {
    private MessageType messageType;
    private String content;

    public Message(MessageType messageType, String content) {
        this.messageType = messageType;
        this.content = content;
    }

    public static Message parseString(String string) {
        String stringMessageType = string.substring(string.indexOf("<") + 1, string.indexOf(">"));
        String stringContent = string.substring(string.indexOf(">") + 1);
        return new Message(MessageType.valueOf(stringMessageType), stringContent);
    }

    public String toString() {
        return "<" + messageType.toString() + ">" + content;
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

}
