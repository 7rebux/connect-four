package de.nosswald;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Nils Osswald, Kai Jellinghaus
 */
public final class GUI extends JFrame {

    private char[][] board;
    private GameRules rules;
    private ArrayList<GUIListener> listeners = new ArrayList<>();

    public void onTileChange(int row, int col, char newValue) {
        board[row][col] = newValue;
        repaint();
    }

    public void addGUIListener(GUIListener listener) {
        listeners.add(listener);
    }

    public GUI(GameRules rules) {
        this.rules = rules;
        this.board = new char[rules.getFieldHeight()][rules.getFieldWidth()];

        this.setTitle("Connect Four");
        this.setSize(800, 600);
        // this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final int row = (e.getY() - 80) / (-10 + GUI.this.getHeight() / rules.getFieldHeight());
                final int col = (e.getX() - 50) / (-10 + GUI.this.getWidth() / rules.getFieldWidth());

                System.out.printf("Pressed on [%d|%d] => [%d][%d]%n", e.getX(), e.getY(), col, row);

                listeners.forEach(x -> x.onTileClick(row, col));
            }
        });

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final int factor = getHeight() / rules.getFieldHeight();

        super.paint(g2d);

        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int row = 0; row < board[0].length; row++) {
            for (int col = 0; col < board.length; col++) {
                char value = board[col][row];

                if (value == Board.Yellow) g2d.setColor(Color.YELLOW);
                else if (value == Board.Red) g2d.setColor(Color.RED);
                else g2d.setColor(Color.WHITE);

                g2d.fillOval(50 + factor * row, 80 + factor * col, factor - 10, factor - 10);
            }
        }
    }
}
