package de.nosswald;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Nils Osswald, Kai Jellinghaus
 */
public class Player {
    private final Network network;
    private final byte id;

    private ArrayList<PlayerListener> listeners = new ArrayList<PlayerListener>();

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    public Player(Socket socket, byte id) {
        this.network = new Network(socket);
        this.id = id;
        network.addListener(new NetworkListener()
        {
            @Override
            public void processMessage(ByteBuffer buffer)
            {
                byte id = buffer.get();
                switch (id) {
                    case 0: {
                        throw new RuntimeException("AAAAAAAAAAAAAAAAAA");
                    }
                    case 1: { // Select Tile
                        System.out.println("Sending Tile Update");
                        listeners.forEach(x -> x.onPlaceTile(buffer.get(), buffer.get()));
                        break;
                    }
                    default: {
                        throw new RuntimeException("UNKNOWN ID " + id);
                    }
                }

            }
        });
    }

    public byte getId()
    {
        return id;
    }

    public MatchListener createMatchListener() {
        return new MatchListener()
        {
            @Override
            public void onTileUpdate(int row, int col, byte newState)
            {
                System.out.println("Received Tile update");
                network.sendMessage(new byte[] {
                        1, (byte)row, (byte)col, newState
                });
            }
        };
    }
}
