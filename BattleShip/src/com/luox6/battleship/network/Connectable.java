package com.luox6.battleship.network;

import java.io.IOException;

public interface Connectable {
    public static final int DEFAULT_PORT = 8189;

    public void connect() throws IOException;
    public void send(String message);
    public String receive();
    public int getPort();
}
