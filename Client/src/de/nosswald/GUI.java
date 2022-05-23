package de.nosswald;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Nils Osswald
 * @author Kai Jellinghaus
 */
public final class GUI extends JFrame {

    private final char[][] board;
    private final GameRules rules;
    private final ArrayList<GUIListener> listeners = new ArrayList<>();
    private char nextColor = 'Y';

    public void onTileChange(int row, int col, char newValue) {
        board[row][col] = newValue;
        if (newValue == 'Y') nextColor = 'R';
        else if (newValue == 'R') nextColor = 'Y';
        else nextColor = ' ';
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
                final int factor = Math.min(getHeight() / (rules.getFieldHeight() + 2), getWidth() / (rules.getFieldWidth() + 2));
                final int row = (e.getY() / factor) - 1;
                final int col = (e.getX() / factor) - 1;

                if (row < 0 || row > rules.getFieldWidth() || col < 0 || col > rules.getFieldHeight()) {
                    return;
                }

                System.out.printf("Pressed on [%d|%d] => [%d][%d]%n", e.getX(), e.getY(), col, row);

                listeners.forEach(x -> x.onTileClick(col));
            }
        });

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        // + 2 because we want to leave one empty row on each side
        final int factor = Math.min(getHeight() / (rules.getFieldHeight() + 2), getWidth() / (rules.getFieldWidth() + 2));

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2d.setColor(Color.WHITE);
        g2d.setFont(Resources.FontPageTitle);
        g2d.drawString("To Move: ", (int)(factor * 0.5), (int)(factor * 0.75));
        double textWidth = Resources.FontPageTitle.getStringBounds("To Move: ", g2d.getFontRenderContext()).getBounds2D().getWidth();
        g2d.setColor(getDerivedColor(nextColor));
        g2d.fillOval((int)((factor * 0.5) + textWidth), (int)(factor * 0.5), (int)(factor * 0.5), (int)(factor * 0.5));

        for (int row = 0; row < board[0].length; row++) {
            for (int col = 0; col < board.length; col++) {
                char value = board[col][row];

                g2d.setColor(getDerivedColor(value));

                // +1 on each axis for the top left padding
                g2d.fillOval(factor * (row + 1), factor * (col + 1), factor, factor);
            }
        }
    }

    private static Color getDerivedColor(char value) {
        if (value == Board.Yellow) return Color.YELLOW;
        else if (value == Board.Red) return Color.RED;
        else return Color.WHITE;
    }
}
