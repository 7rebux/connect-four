package de.nosswald;

public final class GameRules
{
    private int fieldWidth;
    private int fieldHeight;

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
