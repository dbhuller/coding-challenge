package com.newrelic.codingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private BlockingQueue<Integer> queue;

    public Server(BlockingQueue<Integer> blockingQueue) {
        this.queue = blockingQueue;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            Queue mainQ = new Queue(queue);
            new Thread(mainQ).start();
            while (true) {
                executorService.submit(new ClientHandler(serverSocket.accept(), queue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //add stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader inputBuffer;
        private BlockingQueue<Integer> blockingQueue;
        private Pattern nineDigitNum = Pattern.compile("\\d{9}");
        private int inputNum;

        private ClientHandler(Socket socket, BlockingQueue<Integer> blockingQueue) {
            this.clientSocket = socket;
            this.blockingQueue = blockingQueue;
        }
        
        @Override
        public void run() {
            try {
                inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while (true) {
                    inputLine = inputBuffer.readLine();
                    if (inputLine.equals("terminate")) {
                        System.exit(0);
                    }
                    if (inputLine == null || !(nineDigitNum.matcher(inputLine).matches())) {
                        break;
                    }
                    inputNum = Integer.parseInt(inputLine);
                    blockingQueue.put(inputNum);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputBuffer.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }

    
}
