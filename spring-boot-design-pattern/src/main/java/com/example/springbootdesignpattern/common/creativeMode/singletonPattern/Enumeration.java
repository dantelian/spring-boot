package com.example.springbootdesignpattern.common.creativeMode.singletonPattern;

public enum Enumeration {
    INSTANCE;

    private static int i = 0;

    public static int getInt() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i++;
    }


}
