package de.nosswald;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nils Osswald
 * @author Kai Jellinghaus
 */
public final class GameResultGUI extends JFrame {
    public GameResultGUI(char winner) {
        this.setTitle("Result - Connect Four");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setResizable(false);
        this.setLayout(new FlowLayout());

        JLabel label = new JLabel(winner == ' ' ? "The game ended in a tie!" : (winner == Board.Red ? "Red" : "Yellow") + " has won the game!");
        JButton button = new JButton("Exit");

        button.addActionListener(e -> {
            System.exit(0);
        });

        this.add(label);
        this.add(button);

        this.setVisible(true);
    }
}
