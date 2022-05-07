package de.nosswald;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Nils Osswald
 */
public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
