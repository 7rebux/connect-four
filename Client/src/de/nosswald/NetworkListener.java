package de.nosswald;

import java.nio.ByteBuffer;

public interface NetworkListener
{
    void processMessage(ByteBuffer buffer);
}
