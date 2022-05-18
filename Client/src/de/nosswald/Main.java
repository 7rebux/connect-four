package de.nosswald;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Nils Osswald
 */
public class Main {
    public static void main(String[] args) {
        GameRules g = new GameRules(6, 7);
        Board b = new Board();
        Network n = new Network();
        GUI gui = new GUI(g);
    }
}
