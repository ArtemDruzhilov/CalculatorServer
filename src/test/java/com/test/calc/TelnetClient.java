package com.test.calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TelnetClient {

    private final Socket socket;

    public TelnetClient(final String host, final int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public List<String> executeExpressions(final List<String> expressions) throws IOException {
        final List<String> results = new ArrayList<>();

        try (final var out = new PrintWriter(socket.getOutputStream());
             final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            expressions.forEach(expr -> {
                randomDelay();
                out.print(expr + "\r\n");
                out.flush();
                try {
                    results.add(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } finally {
            socket.close();
        }
        return results;
    }

    private void randomDelay() {
        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
