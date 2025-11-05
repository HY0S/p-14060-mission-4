package com.back;

public class Main {
    public static void main(String[] args) {
        try {
            new App().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}