package de.nosswald;

/**
 * @author Nils Osswald
 */
public class Board {
    private Player[] board;
    private Player currentPlayer;

    public Board(Player currentPlayer) {
        this.currentPlayer = currentPlayer;

        board = new Player[48];
    }

    /**
     * @return true if the current player has won the game
     */
    public boolean isGameOver() {
        return false;
    }

    /**
     * @return true if the board is fully filled up
     */
    public boolean isBoardFull() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null)
                return false;
        }
        return true;
    }
}
