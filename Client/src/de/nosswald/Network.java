package de.nosswald;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Nils Osswald
 * @author Kai Jellinghaus
 */
public final class Network
{
    private final Socket socket;
    private final Queue<byte[]> messages = new ArrayBlockingQueue<>(100);
    private final ExecutorService worker = Executors.newCachedThreadPool();
    private final ArrayList<NetworkListener> listeners = new ArrayList<>();

    public Network(Socket socket) {
        this.socket = socket;

        Thread t = new Thread(this::loop);
        t.setDaemon(true);
        t.setName("Network Loop");
        t.start();
    }

    public void addListener(NetworkListener listener) {
        listeners.add(listener);
    }

    public void sendMessage(byte[] message) {
        byte[] m2 = new byte[message.length + 1];
        m2[0] = (byte)message.length;
        for (int i = 0; i < message.length; i++) {
            m2[i + 1] = message[i];
        }
        if (!messages.offer(m2)) {
            throw new RuntimeException(":(");
        }
    }

    private void loop() {
        try
        {
            while (!socket.isConnected()) {
                Thread.yield();
            }

            InputStream i = socket.getInputStream();
            OutputStream o = socket.getOutputStream();

            // use integer to make it a reference....
            Integer nextLength = null;

            while (socket.isConnected())
            {
                if(nextLength == null && i.available() > 0) {
                    // read the length
                    int l = i.read();
                    if (l == -1) {
                        throw new Exception("End of Stream fuck");
                    }
                    nextLength = l;
                    System.out.println("Read Length " + nextLength);
                }
                else if (!messages.isEmpty()) {
                    byte[] toSend = messages.poll();
                    o.write(toSend);
                    o.flush();
                    System.out.println("Send message " + toSend.length);
                }
                else if (nextLength != null && i.available() >= nextLength) {
                    byte[] b = new byte[nextLength];
                    int l = i.read(b);
                    if (l == -1) {
                        throw new Exception("End of Stream fuck");
                    }
                    if (l != b.length) {
                        throw new Exception("Well fuck");
                    }
                    nextLength = null;
                    processMessage(b);
                } else {
                    Thread.yield();
                }
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        System.out.println("End of loop");
    }

    private void processMessage(byte[] message) {
        System.out.println("Received Message");
        ByteBuffer b = ByteBuffer.wrap(message).asReadOnlyBuffer();
        listeners.forEach(x -> worker.execute(() -> x.processMessage(b.duplicate())));
    }
}
