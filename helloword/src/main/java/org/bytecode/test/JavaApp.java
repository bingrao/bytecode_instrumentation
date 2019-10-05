package org.bytecode.test;

/**
 * @author 
 */
public class JavaApp {
    public static void printHello(){
        System.out.println("Hello World from Java");
    }
    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        printHello();
        Dog tuffy = new Dog("tuffy","papillon", 5, "white");
        System.out.println(tuffy.toString());
    }
}