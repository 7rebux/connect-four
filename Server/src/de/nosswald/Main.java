package de.nosswald;

import java.net.ServerSocket;

/**
 * @author Nils Osswald
 */
public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);

            System.out.println("Waiting for players..");

            Player a = new Player(serverSocket.accept());
            Player b = new Player(serverSocket.accept());
            Match match = new Match(a, b);

            System.out.println("Match started");

            serverSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
