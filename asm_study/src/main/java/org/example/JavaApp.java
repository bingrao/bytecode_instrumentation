package org.example;
import org.example.utils.AgentLogger;

/**
 * @author 
 */
public class JavaApp {
    public static AgentLogger log = new AgentLogger("JavaApp");
    public static void printHello(){
        log.info("Hello World from Java");
    }
    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        printHello();
    }
}