package de.nosswald;

/**
 * @author Kai Jellinghaus
 */
public final class GameRules
{
    private final int fieldWidth;
    private final int fieldHeight;

    public GameRules(int fieldWidth, int fieldHeight)
    {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    public int getFieldWidth()
    {
        return fieldWidth;
    }

    public int getFieldHeight()
    {
        return fieldHeight;
    }
}
