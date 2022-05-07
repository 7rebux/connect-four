package de.nosswald;

import java.net.Socket;

public class Player {
    private Socket socket;

    public Player(Socket socket) {
        this.socket = socket;
    }
}
