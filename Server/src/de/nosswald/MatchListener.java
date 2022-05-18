package de.nosswald;

/**
 * @author Kai Jellinghaus
 */
public interface MatchListener
{
    void onTileUpdate(int row, int col, byte newState);
}
