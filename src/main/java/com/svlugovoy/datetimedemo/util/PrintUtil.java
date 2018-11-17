package com.svlugovoy.datetimedemo.util;

public class PrintUtil {

    public static void printMethodName(){
        System.out.println("===== " + Thread.currentThread().getStackTrace()[2].getMethodName() + "() =====");
    }
}
