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
    private char selfColor = ' ';
    private Integer hoverCol = null;

    public void onTileChange(int row, int col, char newValue) {
        board[row][col] = newValue;
        if (newValue == 'Y') nextColor = 'R';
        else if (newValue == 'R') nextColor = 'Y';
        else nextColor = ' ';
        repaint();
    }

    public void onGameInit(char selfColor) {
        System.out.println("Game Initialized");
        this.selfColor = selfColor;
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

                if (row < 0 || row > rules.getFieldHeight() || col < 0 || col > rules.getFieldWidth()) {
                    return;
                }

                System.out.printf("Pressed on [%d|%d] => [%d][%d]%n", e.getX(), e.getY(), col, row);

                listeners.forEach(x -> x.onTileClick(col));
            }
        });

        this.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                final int factor = Math.min(getHeight() / (rules.getFieldHeight() + 2), getWidth() / (rules.getFieldWidth() + 2));
                final int row = (e.getY() / factor) - 1;
                final int col = (e.getX() / factor) - 1;

                if (row < 0 || row > rules.getFieldHeight() || col < 0 || col > rules.getFieldWidth()) {
                    if (hoverCol != null)
                    {
                        hoverCol = null;
                        repaint();
                    }
                    return;
                }

                if (hoverCol == null || hoverCol != col) {
                    System.out.println("Selected Col: " + col);
                    hoverCol = col;
                    repaint();
                }
            }
        });

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        // + 2 because we want to leave one empty row on each side
        final int factor = Math.min(getHeight() / (rules.getFieldHeight() + 2), getWidth() / (rules.getFieldWidth() + 2));

        g2d.setColor(new Color(0x22, 0x22, 0x22));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (selfColor == ' ') {
            g2d.setColor(Color.WHITE);
            g2d.setFont(Resources.FontPageTitle);
            double textWidth = Resources.FontPageTitle.getStringBounds("Waiting for Game to start...", g2d.getFontRenderContext()).getBounds2D().getWidth();
            g2d.drawString("Waiting for Game to start...", (int)(this.getWidth() / 2 - textWidth / 2), this.getHeight() / 2);
            return;
        }

        Integer hoverRow = null;
        if (hoverCol != null && hoverCol >= 0 && hoverCol < board[0].length) {
            for (int row = board.length - 1; row >= 0; --row)  {
                if (board[row][hoverCol] != Board.Red && board[row][hoverCol] != Board.Yellow) {
                    hoverRow = row;
                    System.out.println("Selected transparent indicator at " + hoverCol + " " + hoverRow);
                    break;
                }
            }
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(Resources.FontPageTitle);
        g2d.drawString("To Move: ", (int)(factor * 0.5), (int)(factor * 0.75));
        double textWidth1 = Resources.FontPageTitle.getStringBounds("To Move: ", g2d.getFontRenderContext()).getBounds2D().getWidth();
        g2d.setColor(getDerivedColor(nextColor));
        g2d.fillOval((int)((factor * 0.5) + textWidth1), (int)(factor * 0.5), (int)(factor * 0.5), (int)(factor * 0.5));

        g2d.setColor(Color.WHITE);
        g2d.drawString("You Are: ", (int)(factor * 0.5) + (int)((factor) + textWidth1), (int)(factor * 0.75));
        double textWidth2 = Resources.FontPageTitle.getStringBounds("You Are: ", g2d.getFontRenderContext()).getBounds2D().getWidth();
        g2d.setColor(getDerivedColor(selfColor));
        g2d.fillOval((int)((factor * 0.5) + textWidth2) + (int)((factor) + textWidth1), (int)(factor * 0.5), (int)(factor * 0.5), (int)(factor * 0.5));

        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length; row++) {
                char value = board[row][col];

                Color c = getDerivedColor(value);

                if (hoverRow != null && hoverCol != null && row == hoverRow && col == hoverCol) {
                    c = getDerivedColor(selfColor).darker();
                    System.out.println(c);
                }

                g2d.setColor(c);

                // +1 on each axis for the top left padding
                g2d.fillOval(factor * (col + 1), factor * (row + 1), factor, factor);
            }
        }
    }

    private static Color getDerivedColor(char value) {
        if (value == Board.Yellow) return new Color(245, 232, 47);
        else if (value == Board.Red) return Color.RED;
        else return new Color(0x44, 0x44, 0x44);
    }
}
