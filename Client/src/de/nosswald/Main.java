package de.nosswald;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author Nils Osswald
 */
public class Main {
    public static void main(String[] args) {
        GameRules g = new GameRules(6, 7);
        Network n = new Network();
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
                        byte row = buffer.get();
                        byte col = buffer.get();
                        byte newState = buffer.get();
                        gui.onTileChange(row, col, newState == 0 ? ' ' : (newState == 1 ? Board.Yellow : Board.Red));
                        break;
                    }
                    case 2: { // Win State
                        boolean didIWin = buffer.get() == 1;
                        throw new RuntimeException("TODO: IMPLEMENT WINNER SCREEN");
                    }
                }
            }
        });
    }
}
