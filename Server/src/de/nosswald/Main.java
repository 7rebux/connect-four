package de.nosswald;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Nils Osswald
 */
public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("Server started on port: " + serverSocket.getLocalPort());
            System.out.println("Waiting for players..");

            Player a = new Player(serverSocket.accept(), "RED");
            System.out.println("Player RED connected");
            Player b = new Player(serverSocket.accept(), "YELLOW");
            System.out.println("Player YELLOW connected");

            Match match = new Match(a, b);
            match.start();
            System.out.println("Game started");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
