package de.nosswald;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Nils Osswald
 */
public final class GUI extends JFrame {
    private Board board;

    public GUI(Board board) {
        this.board = board;

        this.setTitle("Connect Four");
        this.setSize(800, 600);
        // this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final int row = (e.getY() - 80) / (-10 + GUI.this.getHeight() / 7);
                final int col = (e.getX() - 50) / (-10 + GUI.this.getHeight() / 7);

                System.out.printf("Pressed on [%d|%d] => [%d][%d]%n", e.getX(), e.getY(), col, row);
            }
        });

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final int factor = getHeight() / 7;

        super.paint(g2d);

        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int row = 0; row < board.getBoard()[0].length; row++) {
            for (int col = 0; col < board.getBoard().length; col++) {
                char value = board.getBoard()[col][row];

                if (value == ' ') g2d.setColor(Color.WHITE);
                else if (value == 'Y') g2d.setColor(Color.YELLOW);
                else if (value == 'R') g2d.setColor(Color.RED);

                g2d.fillOval(50 + factor * row, 80 + factor * col, factor - 10, factor - 10);
            }
        }
    }
}
