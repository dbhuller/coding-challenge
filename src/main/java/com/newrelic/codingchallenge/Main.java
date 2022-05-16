package com.newrelic.codingchallenge;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting up server ....");

        // Add your code here
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        Server server = new Server(queue);
        server.start(4000);
    }
}