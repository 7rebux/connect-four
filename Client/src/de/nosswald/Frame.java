package de.nosswald;

import javax.swing.*;

/**
 * @author Nils Osswald
 */
public final class Frame extends JFrame {
    public Frame() {
        this.setTitle("Connect Four");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setContentPane(new Interface());

        this.setVisible(true);
    }
}
