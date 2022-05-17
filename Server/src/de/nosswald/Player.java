package de.nosswald;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

/**
 * @author Nils Osswald
 */
public class Player extends Thread {
    private final Socket socket;
    private final String color;

    private PrintWriter output;
    private BufferedReader input;

    public Player(Socket socket, String color) {
        this.socket = socket;
        this.color = color;

        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        output.println("MSG Connected");
        output.println("MSG You are " + color.toLowerCase(Locale.ROOT));
    }

    @Override
    public void run() {
        output.println("MSG Game started");

        // RED has the first move
        if (color == "RED") ;

        // game loop
        try {
            while (true) {
                String command = input.readLine();
                System.out.println(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
