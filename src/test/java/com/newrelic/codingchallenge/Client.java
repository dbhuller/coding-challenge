package com.newrelic.codingchallenge;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter output;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error while starting connection");
        }
    }

    public void sendNums() {
        output.print("000000001");
        output.print("002222222");
        output.print("000000001");
        output.print("111111111");

    }

    public void terminate() {
        output.println("terminate");
    }

    public void stopConnection() {
        try {
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error while stopping connection");
        }
    }
    
}
