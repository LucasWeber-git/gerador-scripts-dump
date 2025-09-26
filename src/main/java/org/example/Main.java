package org.example;

public class Main {

    public static void main(String[] args) {
        int qty = 1000000;

        Generator generator = new Generator(qty);
        generator.generate();
    }

}