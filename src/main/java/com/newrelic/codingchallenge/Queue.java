package com.newrelic.codingchallenge;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

public class Queue implements Runnable {
    private LogFileCreator<Integer> logFileCreator = new LogFileCreator<>();
    private BlockingQueue<Integer> blockingQueue;
    private Timer t;

    class Report extends TimerTask {
        private LogFileCreator<Integer> LFC;
        private int unique = 0;
        private int duplicate = 0;
        private int uniqueDiff;
        private int duplicateDiff;
        
        public Report(LogFileCreator<Integer> LFC) {
            this.LFC = LFC;
        }

        @Override
        public void run() {
            uniqueDiff = LFC.size() - unique;
            duplicateDiff = LFC.getDuplicates() - duplicate;
            unique = LFC.size();
            duplicate = LFC.getDuplicates();
            System.out.printf("Recieved %d unique numbers, %d duplicate numbers. Total Unique: %d\n", uniqueDiff, duplicateDiff, LFC.size());
        }
    }

    public Queue(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
        t = new Timer();
        t.schedule(new Report(logFileCreator), 0, 10000);
    }


    @Override
    public void run() {
        while(true) {
            try {
                int buffer = blockingQueue.take();
                logFileCreator.insert(buffer);
            } catch (InterruptedException e) {
                System.out.println("Connection Interrupted");
            }
        }
    }
    
}
