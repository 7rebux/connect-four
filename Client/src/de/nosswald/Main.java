package de.nosswald;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author Nils Osswald
 * @author Kai Jellinghaus
 */
public class Main {
    public static void main(String[] args) {
        GameRules g = new GameRules(7, 6);

        Socket socket = new Socket();
        try
        {
            socket.connect(new InetSocketAddress("localhost", 4317));
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        Network n = new Network(socket);
        GUI gui = new GUI(g);

        n.addListener(new NetworkListener()
        {
            @Override
            public void processMessage(ByteBuffer buffer)
            {
                byte id = buffer.get();
                switch(id) {
                    case 0: {
                        throw new RuntimeException("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    }
                    case 1: { // Tile Update
                        System.out.println("Received Tile update");
                        byte row = buffer.get();
                        byte col = buffer.get();
                        byte newState = buffer.get();
                        gui.onTileChange(row, col, newState == 0 ? ' ' : (newState == 1 ? Board.Yellow : Board.Red));
                        break;
                    }
                    case 2: { // Win State
                        byte s = buffer.get();
                        char winner = s == 0 ? ' ' : (s == 1 ? Board.Yellow : Board.Red);

                        new GameResultGUI(winner);
                        break;
                    }
                    default: {
                        throw new RuntimeException("UNKNOWN ID " + id);
                    }
                }
            }
        });

        gui.addGUIListener(new GUIListener()
        {
            @Override
            public void onTileClick(int row, int col)
            {
                System.out.println("Sending Tile click");
                n.sendMessage(new byte[] {
                        1, // ID
                        (byte)row,
                        (byte)col
                });
            }
        });
    }
}
