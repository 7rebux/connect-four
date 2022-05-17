package de.nosswald;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Nils Osswald
 */
public final class Board {
    private Player[][] board;
    private Player currentPlayer;

    public Board(Player currentPlayer) {
        this.currentPlayer = currentPlayer;

        board = new Player[7][6];
    }

    public boolean makeMove() {

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
}
