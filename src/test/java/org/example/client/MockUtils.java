package org.example.client;

import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.net.InetAddress;

public class MockUtils {


    private static final int TEST_SERVER_PORT = 8080;

    public static MockWebServer startServer() throws IOException {
        MockWebServer server = new MockWebServer();
        server.start(InetAddress.getByName("localhost"), TEST_SERVER_PORT);
        return server;
    }
}
