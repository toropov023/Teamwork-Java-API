package ca.toropov.api.teamwork.event;


import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import me.alexpanov.net.FreePortFinder;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Author: toropov
 * Date: 2/5/2019
 */
public class Listener implements HttpHandler {
    @Getter
    private final int port;

    /**
     * Creates a webhook that listens for events on the first available port from 1100 to 65535.
     */
    public Listener() {
        this(FreePortFinder.findFreeLocalPort());
    }

    /**
     * Creates a webhook that listens for events on the specified port
     *
     * @param port The port to listen for events on
     */
    public Listener(int port) {
        this.port = port;
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", this);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO debug
    public static void main(String... args) {
        new Thread(Listener::new).start();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(200, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JsonObject from = JsonParser.object().from(exchange.getRequestBody());
            //TODO debug
            System.out.println(from.getObject("task").getString("name"));
        } catch (JsonParserException e) {
            e.printStackTrace();
        }
    }
}
