package de.nosswald;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Nils Osswald
 * @author Kai Jellinghaus
 */
public class Match {
    private final Player[] players;
    private final Player[][] board;
    private Player currentPlayer;
    private final ArrayList<MatchListener> listeners = new ArrayList<>();

    public void addMatchListener(MatchListener listener) {
        listeners.add(listener);
    }

    /**
     * @return true if the current player has won the game
     */
    public boolean isGameOver() {
        int width = board.length;
        int height = board[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[col][row] == null)
                    continue;

                // horizontal
                if (col + 3 < width &&
                        currentPlayer == board[col+1][row] &&
                        currentPlayer == board[col+2][row] &&
                        currentPlayer == board[col+3][row])
                    return true;

                if (row + 3 < height) {
                    // vertical
                    if (currentPlayer == board[col][row+1] &&
                            currentPlayer == board[col][row+2] &&
                            currentPlayer == board[col][row+3])
                        return true;

                    // diagonal right
                    if (col + 3 < width &&
                            currentPlayer == board[col+1][row+1] &&
                            currentPlayer == board[col+2][row+2] &&
                            currentPlayer == board[col+3][row+3])
                        return true;

                    // diagonal left
                    if (col - 3 >= 0 &&
                            currentPlayer == board[col-1][row+1] &&
                            currentPlayer == board[col-2][row+2] &&
                            currentPlayer == board[col-3][row+3])
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * @return true if the board is fully filled up
     */
    public boolean isBoardFull() {
        return Arrays.stream(board).flatMap(Arrays::stream).noneMatch(Objects::isNull);
    }

    private void setTile(int row, int col, Player player) {
        this.board[row][col] = player; // TODO logic to drop from top instead.
        byte id = 0;
        if (player != null) {
            id = player.getId();
        }
        byte finalId = id;
        listeners.forEach(x -> x.onTileUpdate(row, col, finalId));
    }

    public Match(Player a, Player b) {
        this.currentPlayer = a;

        board = new Player[7][6];
        players = new Player[2];
        players[0] = a;
        players[1] = b;
    }

    public void tryPlaceTile(int row, int col, Player p)
    {
        if (currentPlayer != p)
        {
            // nice try lol
            return;
        }

        if (isGameOver()) {
            listeners.forEach(x -> x.onPlayerWin(p.getId()));
        }
        else if (isBoardFull()) {
            listeners.forEach(x -> x.onDraw());
        }
        currentPlayer = Arrays.stream(players).filter(x -> x != p).findFirst().orElseThrow(() -> new RuntimeException("FUCK"));
        setTile(row, col, p);
    }
}
