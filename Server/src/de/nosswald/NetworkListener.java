package de.nosswald;

import java.nio.ByteBuffer;

/**
 * @author Kai Jellinghaus
 */
public interface NetworkListener
{
    void processMessage(ByteBuffer buffer);
}
