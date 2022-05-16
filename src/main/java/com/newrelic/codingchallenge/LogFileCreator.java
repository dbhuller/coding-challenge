package com.newrelic.codingchallenge;

import java.io.*;

public class LogFileCreator<Key extends Comparable<Key>> {

    private File file;
    private Node root;
    private int duplicates = 0;

    private class Node {
        private Key key;
        private Node left, right;
        private int size;

        public Node(Key key, int size) {
            this.key = key;
            this.size = size;
        }
    }

    public LogFileCreator() {
        file = new File("./numbers.log");
        if (file.exists()) {
            file.delete();
        }
    }

    public int getDuplicates() {
        return duplicates;
    }

    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) {
            return 0;
        } else {
            return n.size;
        }
    }

    public void insert(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key has NULL value");
        }
        root = insert(root, key);
    }

    public void addToLog(int value) {
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferWriter.write(String.format("%09d", value) + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Unable to read file " + file.toString());
        }
    }

    private Node insert(Node node, Key key) {
        if (node == null) {
            addToLog((Integer) key);
            return new Node(key, 1);
        }
        int comparator = key.compareTo(node.key);
        if (comparator < 0) {
            node.left = insert(node.left, key);
        } else if (comparator > 0) {
            node.right = insert(node.right, key);
        } else {
            duplicates++;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }
    
}
