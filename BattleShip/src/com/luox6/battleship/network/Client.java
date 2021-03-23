package com.luox6.battleship.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This Implementation derived from
 * TicTocToe shown on the lecture
 */
public class Client implements Connectable {
    public Client(String server) {
        this(server, DEFAULT_PORT);
    }

    public Client(String server, int port) {
        this.log = Logger.getLogger("global");
        this.server = server;
        this.port = port;
    }

    public void connect() throws IOException {
        this.socket = new Socket(this.server, this.port);

        log.info(String.format("Connection to server %s established at port %d.\n", server, port));
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

    public String getServer() {
        return this.server;
    }

    public boolean isConnectionClosed() {
        return this.socket.isClosed();
    }

    private Socket socket;
    private String server;
    private int port;
    private InputStream inStream;
    private OutputStream outStream;
    Scanner in;
    PrintWriter out;
    private Logger log;
}
