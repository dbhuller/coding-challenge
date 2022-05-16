package com.newrelic.codingchallenge;

import org.junit.jupiter.api.Test;

public class MainTest {

    private static final Integer PORT = 4000;
    private static final String IP = "127.0.0.1";

    @Test
    public void client1() {
        Client client = new Client();
        client.startConnection(IP, PORT);
        client.sendNums();
        client.stopConnection();
    }
}
