package banana.duo.Common;

public class Message {
    private MessageType messageType;
    private String content;

    private Message(MessageType messageType, String content) {
        this.messageType = messageType;
        this.content = content;
    }

    public static Message parseString(String string) {
        String stringMessageType = string.substring(string.indexOf("<") + 1);
        stringMessageType = string.substring(0, string.indexOf(">"));
        String stringContent = string.substring(string.indexOf(">") + 1);;
        stringContent = string.substring(0, string.indexOf("\n"));
        return new Message(MessageType.valueOf(stringMessageType), stringContent);
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

}
