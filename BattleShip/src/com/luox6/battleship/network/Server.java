package com.luox6.battleship.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;
import java.nio.charset.*;

/**
 * This Implementation derived from
 * TicTocToe shown on the lecture
 */
public class Server implements Connectable {
    public Server () throws IOException {
        this(DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        this.log = Logger.getLogger("global");

        this.port = port;
        this.servSocket = new ServerSocket(this.port);
        log.info(String.format("Server socket was created on port %d.\n", port));
    }

    public void connect() throws IOException {
        this.socket = this.servSocket.accept();
        log.info(String.format("Incoming connection from a client at %s accepted.\n", this.socket.getRemoteSocketAddress().toString()));
        this.inStream =  this.socket.getInputStream();
        this.outStream = this.socket.getOutputStream();
        this.in = new Scanner(this.inStream);
        this.out = new PrintWriter(new OutputStreamWriter(this.outStream, StandardCharsets.UTF_8), true /*autoFlush */);
    }

    public void send(String message) {
        this.out.println(message);
        log.info(String.format("Message %s sent.\n", message));
    }

    public String receive() {
        String message = this.in.nextLine();
        log.info(String.format("Message %s received.\n", message));
        return message;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isConnectionClosed() {
        return this.socket.isClosed();
    }

    private int port;
    private Socket socket;
    private ServerSocket servSocket;
    private InputStream inStream;
    private OutputStream outStream;
    Scanner in;
    PrintWriter out;
    private Logger log;
}
