package by.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Main {
    private static LinkedList<ClientHandler> clients = new LinkedList<>();

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            try {
                server = new ServerSocket(5555);
                while (true) {
                    Socket socket = server.accept();
                    try {
                        clients.add(new ClientHandler(socket));
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            } catch (IOException e) {
            } finally {
                if ((server != null) && !server.isClosed()) {
                    server.close();
                }
            }

        } catch (IOException e) {
        }
    }
}
