package com.luox6.battleship.network;

import java.io.IOException;

/**
 * Interface describe a connectable object
 * Used for Client/Server implementation.
 * This Implementation derived from
 * icTocToe shown on the lecture.
 */
public interface Connectable {
    public static final int DEFAULT_PORT = 8189;

    public void connect() throws IOException;
    public void send(String message);
    public String receive();
    public boolean isConnectionClosed();
    public int getPort();
}
