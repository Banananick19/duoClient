package banana.duo.Client;

import java.io.*;
import java.net.Socket;

import banana.duo.Common.*;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public Client(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    public void sendMessage(Message message) throws IOException {
        out.write(message.toString() + "\n"); // отправляем сообщение на сервер
        out.flush();
    }

    public Message getMessage(MessageType messageType) throws IOException {
        return Message.parseString(in.readLine());
    }
}