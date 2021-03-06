package de.nosswald;

/**
 * @author Nils Osswald
 * @author Kai Jellinghaus
 */
public interface MatchListener
{
    void onTileUpdate(int row, int col, byte newState);

    void onPlayerWin(byte winner);

    void onDraw();

    void onGameInit();
}
