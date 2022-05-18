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
        new GUI(new Board());
    }

    private static class Listener implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket("localhost", 6666);

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    String command = input.readLine();

                    if (command.startsWith("MSG"))
                        System.out.println(command.split("MSG ")[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
