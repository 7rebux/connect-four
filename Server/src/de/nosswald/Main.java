package de.nosswald;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Nils Osswald, Kai Jellinghaus
 */
public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4317);
            System.out.println("Server started on port: " + serverSocket.getLocalPort());
            System.out.println("Waiting for players..");

            Player a = new Player(serverSocket.accept(), (byte)1);
            System.out.println("Player YELLOW connected");
            Player b = new Player(serverSocket.accept(), (byte)2);
            System.out.println("Player RED connected");

            Match match = new Match(a, b);
            match.addMatchListener(a.createMatchListener());
            match.addMatchListener(b.createMatchListener());

            a.addListener(new PlayerListener()
            {
                @Override
                public void onPlaceTile(int row, int col)
                {
                    System.out.println("Tried to place tile as a");
                    match.tryPlaceTile(row, col, a);
                }
            });

            b.addListener(new PlayerListener()
            {
                @Override
                public void onPlaceTile(int row, int col)
                {
                    System.out.println("Tried to place tile as b");
                    match.tryPlaceTile(row, col, b);
                }
            });
            System.out.println("Game started");
            while(true) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
