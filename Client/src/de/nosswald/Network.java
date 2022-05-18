package de.nosswald;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Network
{
    private final Thread thread;
    private final Socket socket;
    private final Queue<byte[]> messages = new ArrayBlockingQueue<>(100);
    private final ExecutorService worker = Executors.newWorkStealingPool();

    public Network() {
        socket = new Socket();
        try
        {
            socket.connect(new InetSocketAddress("localhost", 4317));
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        thread = new Thread(this::Loop);
    }

    private void Loop() {
        try
        {
            InputStream i = socket.getInputStream();
            OutputStream o = socket.getOutputStream();

            // use integer to make it a reference....
            Integer nextLength = null;

            while (socket.isConnected())
            {
                if(nextLength == null) {
                    // read the length
                    int l = i.read();
                    if (l == -1) {
                        throw new Exception("End of Stream fuck");
                    }
                    nextLength = l;
                    continue;
                }

                if (i.available() < nextLength && messages.isEmpty()) {
                    Thread.yield();
                }

                if (!messages.isEmpty()) {
                    byte[] toSend = messages.poll();
                    o.write(toSend);
                    o.flush();
                    continue;
                }

                if (i.available() >= nextLength) {
                    byte[] b = new byte[nextLength];
                    int l = i.read(b);
                    if (l == -1) {
                        throw new Exception("End of Stream fuck");
                    }
                    if (l != b.length) {
                        throw new Exception("Well fuck");
                    }
                    worker.submit(() -> ProcessMessage(b));
                    continue;
                }
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void ProcessMessage(byte[] message) {
        System.out.println("Received Message");
    }
}
